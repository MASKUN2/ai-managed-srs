package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserMessage;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatAssistantClient implements ChatAssistant {
    private final ConversationFinder conversationFinder;

    private final ChatClient client;

    @Override
    public Flux<String> response(Conversation conversation, UserMessage message) {
        return client.prompt()
                .user(message.getContent())
                .stream()
                .content();
    }

    @Override
    public Flux<String> response(UUID conversationId, String request) {
        return client.prompt()
                .user(request)
                .stream()
                .content();
    }
}
