package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatHistory;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatHistoryAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class ChatAssistantClient implements ChatAssistant {

    private final ChatClient client;

    public ChatAssistantClient(ChatClient.Builder builder, ChatMemoryRepository chatMemoryRepository,
                               ChatHistory chatHistory) {
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(4)
                .build();

        this.client = builder
                .defaultSystem("당신은 매우 유능한 비서입니다. 대답은 짧고 간결하게 얘기합니다.")
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        ChatHistoryAdvisor.builder(chatHistory).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();
    }

    @Override
    public Flux<String> response(String conversationId, String request) {
        return client.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(request)
                .stream()
                .content();
    }
}
