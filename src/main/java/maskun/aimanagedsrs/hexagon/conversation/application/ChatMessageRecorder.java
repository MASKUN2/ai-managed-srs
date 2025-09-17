package maskun.aimanagedsrs.hexagon.conversation.application;

import org.springframework.ai.chat.messages.Message;

import java.util.List;

/**
 * 채팅 메세지가 발생한 경우 해당 이벤트를 퍼블리싱합니다.
 */
public interface ChatMessageRecorder {

    default void add(String conversationId, Message message) {
        add(conversationId, List.of(message));
    }

    void add(String conversationId, List<Message> messages);
}
