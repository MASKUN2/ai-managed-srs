package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.Getter;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Getter
public class StreamingMessageResponse {
    private final UUID conversationId;
    private final Flux<String> stream;

    public StreamingMessageResponse(UUID conversationId, Flux<String> stream) {
        this.conversationId = conversationId;
        this.stream = stream;
    }
}
