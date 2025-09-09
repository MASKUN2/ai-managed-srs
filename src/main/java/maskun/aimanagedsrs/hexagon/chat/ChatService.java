package maskun.aimanagedsrs.hexagon.chat;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.chat.provided.Chat;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService implements Chat {

    private final ChatClient client;

    @Override
    public String send(String message) {
        return client.prompt()
                .user(message)
                .call()
                .content();
    }
}
