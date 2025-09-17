package maskun.aimanagedsrs.hexagon.conversation.application.service;

import maskun.aimanagedsrs.hexagon.conversation.application.ChatAssistantBuilder;
import maskun.aimanagedsrs.hexagon.conversation.application.ChatMessageEventPublisher;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;
import maskun.aimanagedsrs.hexagon.conversation.provided.ChatService;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
public class ChatServiceImpl implements ChatService {
    private final ConversationFinder conversationFinder;
    private final ChatAssistant chatAssistant;

    public ChatServiceImpl(ConversationFinder conversationFinder,
                           ChatAssistantBuilder chatAssistantBuilder,
                           ChatMessageEventPublisher chatMessageEventPublisher) {
        this.conversationFinder = conversationFinder;

        final String defaultInstruction = "당신은 유능한 비서로 정확한 정보를 바탕으로 짧고 간결하게 대답합니다.";
        final int chatMemorySize = 4;
        this.chatAssistant = chatAssistantBuilder.build(defaultInstruction, chatMemorySize, chatMessageEventPublisher);
    }

    @Override
    public Flux<String> chat(UUID conversationId, String request) {
        conversationFinder.require(conversationId);
        return chatAssistant.response(conversationId, request);
    }

}
