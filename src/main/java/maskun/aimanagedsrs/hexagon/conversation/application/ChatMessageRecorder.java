package maskun.aimanagedsrs.hexagon.conversation.application;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

/**
 * 채팅 메세지를 기록합니다.
 * 해당 메소드는 @{@link org.springframework.ai.chat.client.advisor.api.StreamAdvisor#adviseStream(ChatClientRequest, StreamAdvisorChain)}
 * 에서 실행될 수 있습니다. 기존 동작 스레드가 리액티브 스레드이므로 스프링 프록시 트랜젝션(JPA)사용에 제약이 있습니다. 그러므로
 * 제약에 구애받지 않는 구현이 필요합니다.
 */
public interface ChatMessageRecorder {

    default void record(String conversationId, Message message) {
        record(conversationId, List.of(message));
    }

    void record(String conversationId, List<Message> messages);
}
