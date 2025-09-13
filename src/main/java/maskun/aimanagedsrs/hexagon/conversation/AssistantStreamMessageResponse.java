package maskun.aimanagedsrs.hexagon.conversation;

import lombok.Getter;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Getter
public class AssistantStreamMessageResponse {
    private final UUID conversationId;
    private final Flux<String> stream;

    public AssistantStreamMessageResponse(UUID conversationId, Flux<String> stream) {
        this.conversationId = conversationId;
        this.stream = stream;

    }
}
