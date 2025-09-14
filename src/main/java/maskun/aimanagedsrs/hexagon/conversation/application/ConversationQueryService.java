package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationQueryService implements ConversationFinder {
    private final ConversationRepository repository;

    @Override
    public Optional<Conversation> find(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Class<Conversation> getEntityClass() {
        return Conversation.class;
    }
}
