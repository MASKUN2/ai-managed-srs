package maskun.aimanagedsrs.hexagon.conversation.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.MessageRequest;
import maskun.aimanagedsrs.hexagon.conversation.domain.AssistantClient;
import maskun.aimanagedsrs.hexagon.conversation.domain.ResponseRecorder;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserMessage;
import maskun.aimanagedsrs.hexagon.conversation.provided.ChatService;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final AssistantClient assistantClient;
    private final ConversationFinder conversationFinder;
    private final ResponseRecorder responseRecorder;
    private final ConversationRepository conversationRepository;

    @Transactional
    @Override
    public StreamingMessageResponse getStreamResponse(MessageRequest request) {
        var conversation = conversationFinder.require(request.conversationId());

        UserMessage tempMessage = UserMessage.of().content(request.content());
        UserMessage userMessage = conversation.append(tempMessage);

        conversationRepository.save(conversation);

        Flux<String> streamResponse = assistantClient.getStreamResponse(conversation, userMessage);

        Flux<String> recordingStream = responseRecorder.record(streamResponse, conversation);

        return new StreamingMessageResponse(conversation, recordingStream);
    }
}
