package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;
import maskun.aimanagedsrs.hexagon.conversation.domain.ConversationService;
import maskun.aimanagedsrs.hexagon.conversation.provided.ChatService;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceFacade implements ChatService {
    private final ChatAssistant chatAssistant;
    private final ConversationFinder conversationFinder;
    private final ConversationRepository conversationRepository;
    private final ConversationService conversationService;

    @Override
    public Flux<String> chat(UUID conversationId, String request) {
        return conversationService.chat(conversationId, request);
    }

}
