package maskun.aimanagedsrs.hexagon.conversation.domain;

import reactor.core.publisher.Flux;

public interface ChatAssistant {
    Flux<String> response(String conversationId, String request);
}
