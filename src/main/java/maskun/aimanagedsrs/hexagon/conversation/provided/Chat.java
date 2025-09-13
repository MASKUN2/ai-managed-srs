package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.UserChatRequest;
import reactor.core.publisher.Flux;

public interface Chat {

    Flux<String> send(UserChatRequest request);
}
