package maskun.aimanagedsrs.hexagon.conversation.domain;

import org.springframework.ai.chat.messages.Message;

public record ChatMessageAddEvent(String conversationId, Message message) {
}
