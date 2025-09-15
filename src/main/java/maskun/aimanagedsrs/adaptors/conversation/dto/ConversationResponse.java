package maskun.aimanagedsrs.adaptors.conversation.dto;

import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;

import java.util.UUID;

public record ConversationResponse(
        UUID conversationId
) {
    public static ConversationResponse of(Conversation conversation) {
        return new ConversationResponse(conversation.getId());
    }
}
