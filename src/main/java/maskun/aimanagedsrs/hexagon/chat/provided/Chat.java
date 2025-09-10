package maskun.aimanagedsrs.hexagon.chat.provided;

import maskun.aimanagedsrs.hexagon.chat.UserChatRequest;
import reactor.core.publisher.Flux;

public interface Chat {

    Flux<String> send(UserChatRequest request);
}
