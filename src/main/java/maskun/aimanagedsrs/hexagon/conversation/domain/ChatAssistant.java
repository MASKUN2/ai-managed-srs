package maskun.aimanagedsrs.hexagon.conversation.domain;

import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserMessage;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ChatAssistant {
    Flux<String> response(Conversation conversation, UserMessage message);

    Flux<String> response(UUID conversationId, String request);
}
