package maskun.aimanagedsrs.hexagon.conversation.domain;

import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import reactor.core.publisher.Flux;

public interface ResponseRecorder {

    Flux<String> record(Flux<String> streamResponse, Conversation conversation);

}
