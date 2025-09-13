package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.AssistantStreamMessageResponse;
import maskun.aimanagedsrs.hexagon.conversation.UserMessageRequest;

public interface ConversationResponseGenerator {

    AssistantStreamMessageResponse getResponse(UserMessageRequest request);
}
