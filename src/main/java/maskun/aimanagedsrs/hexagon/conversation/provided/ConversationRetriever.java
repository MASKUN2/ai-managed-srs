package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.domain.Conversation;

import java.util.Optional;
import java.util.UUID;

public interface ConversationRetriever {
    Optional<Conversation> findById(UUID id);
}
