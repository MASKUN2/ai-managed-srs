package maskun.aimanagedsrs.hexagon.chat;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.chat.provided.Chat;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatService implements Chat {

    private final ChatClient client;

    @Override
    public Flux<String> send(UserChatRequest request) {
        return client.prompt()
                .user(request.message())
                .stream()
                .content();
    }
}
