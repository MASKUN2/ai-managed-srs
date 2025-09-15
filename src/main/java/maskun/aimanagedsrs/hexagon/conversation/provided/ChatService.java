package maskun.aimanagedsrs.hexagon.conversation.provided;

import reactor.core.publisher.Flux;

import java.util.UUID;


public interface ChatService {

    Flux<String> chat(UUID conversationId, String request);
}
