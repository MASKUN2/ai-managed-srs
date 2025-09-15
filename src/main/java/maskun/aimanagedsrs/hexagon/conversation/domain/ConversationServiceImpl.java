package maskun.aimanagedsrs.hexagon.conversation.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConversationServiceImpl implements ConversationService {
    private final ChatAssistant chatAssistant;
    private final ConversationFinder conversationFinder;
    private final ConversationRepository conversationRepository;

    @Override
    public Flux<String> chat(UUID conversationId, String request) {
        var conversation = conversationFinder.require(conversationId);
        conversation.addUserMessage(request);
        conversationRepository.save(conversation);

        log.info("thread: " + Thread.currentThread() + "collected: " + request);

        Flux<String> streamResponse = chatAssistant.response(conversationId, request)
                .cache();

        streamResponse.subscribeOn(Schedulers.boundedElastic())
                .collect(Collectors.joining())
                .subscribe(x -> {
                    System.out.println("thread: " + Thread.currentThread() + "collected: " + x);
                    conversation.addAssistantMessage(x);
                    conversationRepository.save(conversation);
                });

        return streamResponse;
    }

}
