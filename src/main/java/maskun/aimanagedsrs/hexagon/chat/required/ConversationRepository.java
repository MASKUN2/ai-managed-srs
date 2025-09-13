package maskun.aimanagedsrs.hexagon.chat.required;

import maskun.aimanagedsrs.hexagon.chat.domain.Conversation;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface ConversationRepository extends Repository<Conversation, UUID> {
    Conversation save(Conversation conversation);

    Optional<Conversation> findById(UUID id);
}