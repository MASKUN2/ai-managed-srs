package maskun.aimanagedsrs.hexagon.conversation.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.AssistantMessage;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResponseRecorderImpl implements ResponseRecorder {
    private final ConversationFinder conversationFinder;
    private final ConversationRepository conversationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Flux<String> record(Flux<String> streamResponse, Conversation conversation) {
        final UUID conversationId = conversation.getId();
        final StringBuffer buffer = new StringBuffer();

        return streamResponse
                .doOnNext(buffer::append)
                .doFinally(signalType -> {
                    eventPublisher.publishEvent(new CompleteResponseEvent(conversationId, buffer.toString()));
                });
    }

    @Async
    @EventListener
    @Transactional
    public void listen(CompleteResponseEvent event) {
        Conversation conversation = conversationFinder.require(event.conversationId());
        AssistantMessage assistantMessage = AssistantMessage.of().content(event.content());
        conversation.append(assistantMessage);
        conversationRepository.save(conversation);
    }
}
