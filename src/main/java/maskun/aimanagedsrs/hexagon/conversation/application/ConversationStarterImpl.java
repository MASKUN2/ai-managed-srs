package maskun.aimanagedsrs.hexagon.conversation.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationStarter;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationStarterImpl implements ConversationStarter {

    private final ConversationRepository conversationRepository;

    @Override
    @Transactional
    public Conversation startNew() {
        Conversation newConversation = Conversation.startNew();
        return conversationRepository.save(newConversation);
    }
}
