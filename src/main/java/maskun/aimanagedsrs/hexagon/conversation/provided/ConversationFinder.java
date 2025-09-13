package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.domain.Conversation;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public interface ConversationFinder {
    Optional<Conversation> find(UUID id);

    default Conversation require(UUID id) throws NoSuchElementException {
        return find(id).orElseThrow(() -> new NoSuchElementException(Conversation.class.getSimpleName() + " not found for id: " + id));
    }
}
