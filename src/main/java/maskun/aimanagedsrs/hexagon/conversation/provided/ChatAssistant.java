package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.UserMessageRequest;
import maskun.aimanagedsrs.hexagon.conversation.application.AssistantStreamMessageResponse;

public interface ChatAssistant {

    AssistantStreamMessageResponse getResponse(UserMessageRequest request);
}
