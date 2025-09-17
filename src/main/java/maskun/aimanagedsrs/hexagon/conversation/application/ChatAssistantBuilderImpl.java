package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatAssistantBuilderImpl implements ChatAssistantBuilder {
    private final ChatClient.Builder chatClientBuilder;
    private final ChatMemoryRepository chatMemoryRepository;

    @Override
    public ChatAssistant build(String defaultInstruction, int chatMemorySize,
                               ChatMessageEventPublisher chatMessageEventPublisher) {
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(chatMemorySize)
                .build();

        ChatClient chatClient = chatClientBuilder
                .defaultSystem(defaultInstruction)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        ChatMessageRecordAdvisor.builder(chatMessageEventPublisher).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();

        return new ChatAssistantClient(chatClient);
    }
}
