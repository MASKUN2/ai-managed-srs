package maskun.aimanagedsrs.hexagon.conversation.domain;

import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ConversationService {

    Flux<String> chat(UUID conversationId, String request);
}
