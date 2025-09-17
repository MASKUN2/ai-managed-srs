package maskun.aimanagedsrs.hexagon.conversation.application;

import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ChatAssistant {
    Flux<String> response(UUID conversationId, String request);
}
