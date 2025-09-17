package maskun.aimanagedsrs.hexagon.conversation.application;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

public interface ChatMessageEventPublisher {

    String DEFAULT_CONVERSATION_ID = ChatMemory.DEFAULT_CONVERSATION_ID;

    default void add(String conversationId, Message message) {
        add(conversationId, List.of(message));
    }

    void add(String conversationId, List<Message> messages);
}
