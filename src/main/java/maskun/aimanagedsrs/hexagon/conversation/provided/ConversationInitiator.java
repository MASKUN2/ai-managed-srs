package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.domain.Conversation;

public interface ConversationInitiator {
    Conversation startNew();
}
