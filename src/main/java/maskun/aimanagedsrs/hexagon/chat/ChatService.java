package maskun.aimanagedsrs.hexagon.chat;

import maskun.aimanagedsrs.hexagon.chat.provided.Chat;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService implements Chat {

    private final ChatClient client;

    public ChatService(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    @Override
    public String send(String message) {
        return client.prompt()
                .user(message)
                .call()
                .content();
    }
}
