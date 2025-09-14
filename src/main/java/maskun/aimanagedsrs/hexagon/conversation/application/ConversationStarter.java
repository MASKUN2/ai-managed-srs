package maskun.aimanagedsrs.hexagon.conversation.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationInitiator;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationStarter implements ConversationInitiator {

    private final ConversationRepository conversationRepository;

    @Override
    @Transactional
    public Conversation startNew() {
        return conversationRepository.save(Conversation.startNew());
    }
}
