package maskun.aimanagedsrs.hexagon.chat.provided;

import maskun.aimanagedsrs.hexagon.chat.domain.Conversation;

import java.util.Optional;
import java.util.UUID;

public interface ConversationFinder {
    Optional<Conversation> findById(UUID id);
}
