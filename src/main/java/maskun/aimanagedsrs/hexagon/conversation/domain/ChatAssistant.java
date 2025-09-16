package maskun.aimanagedsrs.hexagon.conversation.domain;

import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserChatMessage;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ChatAssistant {
    Flux<String> response(Conversation conversation, UserChatMessage message);

    Flux<String> response(UUID conversationId, String request);

    Flux<String> response(UUID conversationId, String request, ConversationService conversationService);
}
