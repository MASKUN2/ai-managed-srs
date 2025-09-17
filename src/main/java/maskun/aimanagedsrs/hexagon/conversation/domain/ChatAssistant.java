package maskun.aimanagedsrs.hexagon.conversation.domain;

import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ChatAssistant {
    Flux<String> response(UUID conversationId, String request);
}
