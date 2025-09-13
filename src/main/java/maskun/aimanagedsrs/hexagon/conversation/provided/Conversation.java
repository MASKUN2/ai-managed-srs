package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.UserChatRequest;
import reactor.core.publisher.Flux;

public interface Conversation {

    Flux<String> send(UserChatRequest request);
}
