package maskun.aimanagedsrs.hexagon.conversation.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.MessageRequest;
import maskun.aimanagedsrs.hexagon.conversation.domain.AssistantClient;
import maskun.aimanagedsrs.hexagon.conversation.domain.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.Message;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationInitiator;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConversationAssistant implements maskun.aimanagedsrs.hexagon.conversation.provided.ConversationAssistant, ConversationInitiator {
    private final ConversationFinder finder;
    private final ConversationRepository conversationRepository;
    private final AssistantClient assistantClient;

    @Override
    @Transactional
    public StreamingMessageResponse getStreamResponse(MessageRequest request) {
        Conversation conversation = finder.require(request.conversationId());
        Message message = conversation.addUserMessage(request.content());

        final StringBuffer responseBuffer = new StringBuffer();

        Flux<String> responseStream = assistantClient.getResponse(conversation, request.content())
                .doOnNext(responseBuffer::append)
                .doOnComplete(() -> {
                    conversation.addAssistantMessage(responseBuffer.toString());
                    conversationRepository.save(conversation);
                });

        return new StreamingMessageResponse(conversation.getId(), responseStream);
    }

    @Override
    @Transactional
    public Conversation startNew() {
        return conversationRepository.save(Conversation.startNew());
    }
}
