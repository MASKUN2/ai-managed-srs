package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.MessageRequest;
import maskun.aimanagedsrs.hexagon.conversation.application.StreamingMessageResponse;

public interface StreamingAssistantResponseGenerator {

    StreamingMessageResponse getStreamResponse(MessageRequest request);
}
