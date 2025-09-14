package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.domain.Conversation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssistantClient {
    private final ChatClient client;

    public Flux<String> getResponse(Conversation conversation, String userMessageContent) {

        final StringBuffer responseBuffer = new StringBuffer();

        return client.prompt()
                .user(userMessageContent)
                .stream()
                .content()
                .doOnNext(responseBuffer::append)
                .doOnComplete(() -> log.info("Chat response: {}", responseBuffer.toString()));
    }
}
