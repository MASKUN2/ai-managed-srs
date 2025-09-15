package maskun.aimanagedsrs.hexagon.conversation.domain;

import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserMessage;
import reactor.core.publisher.Flux;

public interface ChatAssistant {
    Flux<String> response(Conversation conversation, UserMessage message);
}
