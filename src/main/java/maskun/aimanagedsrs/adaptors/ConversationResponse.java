package maskun.aimanagedsrs.adaptors;

import maskun.aimanagedsrs.hexagon.conversation.domain.Conversation;

import java.util.UUID;

public record ConversationResponse(
        UUID conversationId
) {
    public static ConversationResponse of(Conversation conversation) {
        return new ConversationResponse(conversation.getId());
    }
}
