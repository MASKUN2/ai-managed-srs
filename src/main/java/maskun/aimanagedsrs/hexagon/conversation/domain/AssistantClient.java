package maskun.aimanagedsrs.hexagon.conversation.domain;

import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserMessage;
import reactor.core.publisher.Flux;

public interface AssistantClient {
    Flux<String> getStreamResponse(Conversation conversation, UserMessage message);
}
