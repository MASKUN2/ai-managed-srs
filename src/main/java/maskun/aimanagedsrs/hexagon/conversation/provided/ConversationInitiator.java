package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;

public interface ConversationInitiator {
    Conversation startNew();
}
