package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.UserNewMessage;
import reactor.core.publisher.Flux;

public interface Conversator {

    Flux<String> send(UserNewMessage request);
}
