package maskun.aimanagedsrs.hexagon.conversation.provided;

import reactor.core.publisher.Flux;

public interface AssistantClient {

    Flux<String> getStreamResponse(String content);
}
