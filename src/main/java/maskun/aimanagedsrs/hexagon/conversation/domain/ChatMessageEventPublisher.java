package maskun.aimanagedsrs.hexagon.conversation.domain;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

public interface ChatMessageEventPublisher {

    String DEFAULT_CONVERSATION_ID = ChatMemory.DEFAULT_CONVERSATION_ID;

    default void chatMessageAddEvent(String conversationId, Message message) {
        chatMessageAddEvent(conversationId, List.of(message));
    }

    void chatMessageAddEvent(String conversationId, List<Message> messages);
}
