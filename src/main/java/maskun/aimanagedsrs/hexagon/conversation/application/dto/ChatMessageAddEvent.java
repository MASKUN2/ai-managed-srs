package maskun.aimanagedsrs.hexagon.conversation.application.dto;

import org.springframework.ai.chat.messages.Message;

public record ChatMessageAddEvent(String conversationId, Message message) {
}
