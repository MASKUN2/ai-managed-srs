package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.Getter;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import reactor.core.publisher.Flux;

@Getter
public class StreamingMessageResponse {
    private final Conversation conversation;
    private final Flux<String> stream;

    public StreamingMessageResponse(Conversation conversation, Flux<String> stream) {
        this.conversation = conversation;
        this.stream = stream;
    }
}
