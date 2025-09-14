package maskun.aimanagedsrs.hexagon.conversation.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.MessageRequest;
import maskun.aimanagedsrs.hexagon.conversation.domain.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.ConversationService;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationAssistant;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService implements ConversationAssistant {
    private final ConversationFinder finder;
    private final ConversationService conversationService;

    @Override
    @Transactional
    public StreamingMessageResponse getStreamResponse(MessageRequest request) {
        Conversation conversation = finder.require(request.conversationId());

        var responseStream = conversationService.converse(conversation, request.content());

        return new StreamingMessageResponse(conversation.getId(), responseStream);
    }

}
