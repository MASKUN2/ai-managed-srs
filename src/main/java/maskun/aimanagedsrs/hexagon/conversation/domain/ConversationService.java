package maskun.aimanagedsrs.hexagon.conversation.domain;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ConversationService {
    private final AssistantClient assistantClient;
    private final ConversationRepository conversationRepository;

    @Transactional
    public Flux<String> converse(Conversation conversation, String userMessage) {
        conversation.addUserMessage(userMessage);

        final StringBuffer responseBuffer = new StringBuffer();

        Flux<String> responseStream = assistantClient.getResponse(conversation, userMessage)
                .doOnNext(responseBuffer::append)
                .doOnComplete(() -> {
                    conversation.addAssistantMessage(responseBuffer.toString());
                    conversationRepository.save(conversation);
                });

        return responseStream;
    }
}
