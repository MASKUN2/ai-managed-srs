package maskun.aimanagedsrs.hexagon.conversation.application;

import jakarta.transaction.Transactional;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.provided.ChatService;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationStarter;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class ConversationService implements ChatService, ConversationStarter {
    private final ConversationRepository conversationRepository;
    private final ConversationFinder conversationFinder;
    private final ChatAssistant chatAssistant;

    public ConversationService(
            ConversationRepository conversationRepository,
            ConversationFinder conversationFinder,
            ChatAssistantBuilder chatAssistantBuilder,
            ChatMessageRecorder chatMessageRecorder
    ) {
        this.conversationRepository = conversationRepository;
        this.conversationFinder = conversationFinder;

        final String defaultInstruction = "당신은 유능한 비서로 정확한 정보를 바탕으로 짧고 간결하게 대답합니다.";
        final int chatMemorySize = 4;
        this.chatAssistant = chatAssistantBuilder.build(defaultInstruction, chatMemorySize, chatMessageRecorder);
    }

    @Override
    public Flux<String> chat(UUID conversationId, String request) {
        conversationFinder.require(conversationId);
        return chatAssistant.response(conversationId, request);
    }

    @Override
    @Transactional
    public Conversation startNew() {
        Conversation newConversation = Conversation.startNew();
        return conversationRepository.save(newConversation);
    }

}
