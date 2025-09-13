package maskun.aimanagedsrs.hexagon.conversation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.domain.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.Message;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationResponseGenerator;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConversationService implements ConversationResponseGenerator {
    private final ConversationFinder finder;
    private final AssistantClient assistantClient;

    @Override
    @Transactional
    public AssistantStreamMessageResponse getResponse(UserMessageRequest request) {
        Conversation conversation = finder.require(request.conversationId());
        Message message = conversation.addUserMessage(request.content());

        final StringBuffer responseBuffer = new StringBuffer();

        Flux<String> responseStream = assistantClient.getResponse(conversation, request.content());

        return new AssistantStreamMessageResponse(conversation.getId(), responseStream);
    }
}
