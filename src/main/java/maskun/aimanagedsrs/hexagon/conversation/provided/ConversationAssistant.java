package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.MessageRequest;
import maskun.aimanagedsrs.hexagon.conversation.application.StreamingMessageResponse;

public interface ConversationAssistant {

    StreamingMessageResponse getStreamResponse(MessageRequest request);
}
