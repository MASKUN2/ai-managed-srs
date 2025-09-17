package maskun.aimanagedsrs.hexagon.conversation.application;

import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;

public interface ChatAssistantBuilder {
    ChatAssistant build(String defaultInstruction, int chatMemorySize,
                        ChatMessageRecorder chatMessageRecorder);
}
