package maskun.aimanagedsrs.hexagon.conversation.application;

import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import reactor.core.publisher.Flux;

import java.util.UUID;


public class ChatAssistantClient implements ChatAssistant {
    private final ChatClient client;

    public ChatAssistantClient(ChatClient client) {
        this.client = client;
    }

    @Override
    public Flux<String> response(UUID conversationId, String request) {

        return client.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId.toString()))
                .user(request)
                .stream()
                .content();
    }
}
