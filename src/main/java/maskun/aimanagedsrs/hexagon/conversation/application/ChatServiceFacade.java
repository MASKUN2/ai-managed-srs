package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.AssistantMessage;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserMessage;
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

    @Override
    public Flux<String> chat(UUID conversationId, String request) {
        var conversation = conversationFinder.require(conversationId);

        UserMessage userMessage = addUserMessage(conversation, request);
        AssistantMessage emptyAssistantMessage = addEmptyAssistantMessage(conversation);

        Flux<String> streamResponse = chatAssistant.response(conversation, userMessage);

        final StringBuffer buffer = new StringBuffer();

        return streamResponse
                .doOnNext(buffer::append)
                .doFinally(signalType -> {
                    emptyAssistantMessage.content(buffer.toString());
                    conversationRepository.save(conversation);
                });
    }

    private static UserMessage addUserMessage(Conversation conversation, String request) {
        UserMessage message = UserMessage.of().content(request);
        return conversation.append(message);
    }

    private static AssistantMessage addEmptyAssistantMessage(Conversation conversation) {
        AssistantMessage emptyMessage = AssistantMessage.of();
        return conversation.append(emptyMessage);
    }
}
