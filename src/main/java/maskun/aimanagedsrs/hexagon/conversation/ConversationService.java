package maskun.aimanagedsrs.hexagon.conversation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.provided.Conversation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConversationService implements Conversation {

    private final ChatClient client;

    @Override
    public Flux<String> send(UserChatRequest request) {
        final StringBuffer responseBuffer = new StringBuffer();

        return client.prompt()
                .user(request.message())
                .stream()
                .content()
                .doOnNext(responseBuffer::append)
                .doOnComplete(() -> log.info("Chat response: {}", responseBuffer.toString()));
    }
}
