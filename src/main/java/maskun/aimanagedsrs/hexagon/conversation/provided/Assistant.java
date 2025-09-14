package maskun.aimanagedsrs.hexagon.conversation.provided;

import maskun.aimanagedsrs.hexagon.conversation.UserChatRequestMessage;
import maskun.aimanagedsrs.hexagon.conversation.application.AssistantChatStreamResponse;

/**
 * 채팅 어시스턴트
 */
public interface Assistant {

    AssistantChatStreamResponse response(UserChatRequestMessage request);
}
