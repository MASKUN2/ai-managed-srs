package maskun.aimanagedsrs.hexagon.conversation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationResponseGenerator;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConversationService implements ConversationResponseGenerator {

    private final ChatClient client;

    @Override
    public Flux<String> getResponse(UserMessageRequest request) {
        final StringBuffer responseBuffer = new StringBuffer();

        return client.prompt()
                .user(request.content())
                .stream()
                .content()
                .doOnNext(responseBuffer::append)
                .doOnComplete(() -> log.info("Chat response: {}", responseBuffer.toString()));
    }
}
