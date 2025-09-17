package maskun.aimanagedsrs.hexagon.conversation.application.implement;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.application.ChatAssistantBuilder;
import maskun.aimanagedsrs.hexagon.conversation.application.ChatMessageRecorder;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class ChatAssistantBuilderImpl implements ChatAssistantBuilder {
    private final ChatClient.Builder chatClientBuilder;
    private final ChatMemoryRepository chatMemoryRepository;

    @Override
    public ChatAssistant build(String defaultInstruction, int chatMemorySize,
                               ChatMessageRecorder chatMessageRecorder) {

        Assert.isTrue(chatMemorySize > 0, "chatMemorySize는 1 이상이어야 합니다");

        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(chatMemorySize)
                .build();

        ChatClient chatClient = chatClientBuilder
                .defaultSystem(defaultInstruction)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        ChatMessageHistoryAdvisor.builder(chatMessageRecorder).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();

        return new ChatAssistantClient(chatClient);
    }
}
