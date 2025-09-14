package maskun.aimanagedsrs.hexagon.conversation.domain;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.AssistantMessage;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

@Service
@RequiredArgsConstructor
public class ResponseRecorderImpl implements ResponseRecorder {
    private final TransactionalOperator transactionalOperator;

    @Override
    public Flux<String> record(Flux<String> streamResponse, Conversation conversation) {
        final StringBuffer buffer = new StringBuffer();
        return streamResponse
                .doOnNext(buffer::append)
                .doFinally(signalType -> doFinally(signalType, conversation, buffer));
    }

    private void doFinally(SignalType signalType, Conversation conversation, StringBuffer buffer) {

        if (signalType != SignalType.ON_COMPLETE) {
            buffer.append("--서버 오류가 발생하였습니다.--");
        }
        AssistantMessage assistantMessage = AssistantMessage.of().content(buffer.toString());
        conversation.append(assistantMessage);
    }
}
