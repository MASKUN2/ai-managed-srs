package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.shared.RequirableEntityFinder;

import java.util.UUID;

public interface ConversationFinder extends RequirableEntityFinder<Conversation, UUID> {
}
