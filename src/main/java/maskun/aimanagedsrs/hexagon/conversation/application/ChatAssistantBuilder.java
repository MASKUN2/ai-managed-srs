package maskun.aimanagedsrs.hexagon.conversation.application;

public interface ChatAssistantBuilder {
    ChatAssistant build(String defaultInstruction, int chatMemorySize,
                        ChatMessageRecorder chatMessageRecorder);
}
