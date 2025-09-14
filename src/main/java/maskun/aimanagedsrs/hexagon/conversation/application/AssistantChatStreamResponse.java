package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.Getter;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import reactor.core.publisher.Flux;

@Getter
public class AssistantChatStreamResponse {
    private final Conversation conversation;
    private final Flux<String> contentStream;

    public AssistantChatStreamResponse(Conversation conversation, Flux<String> contentStream) {
        this.conversation = conversation;
        this.contentStream = contentStream;
    }
}
