package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Slf4j
@Service
public class ChatAssistantClient implements ChatAssistant {

    final static int DEFAULT_CHAT_MEMORY_SIZE = 4;

    private final ChatClient client;
    private final ConversationFinder conversationFinder;

    public ChatAssistantClient(ChatClient.Builder builder,
                               ChatMemoryRepository chatMemoryRepository,
                               ChatMessageEventPublisher chatMessageEventPublisher,
                               ConversationFinder conversationFinder) {
        this.conversationFinder = conversationFinder;

        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
                .maxMessages(DEFAULT_CHAT_MEMORY_SIZE)
                .build();

        this.client = builder
                .defaultSystem("당신은 매우 유능한 비서입니다. 대답은 짧고 간결하게 얘기합니다.")
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        ChatMessageRecordAdvisor.builder(chatMessageEventPublisher).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();
    }

    @Override
    public Flux<String> response(UUID conversationId, String request) {
        conversationFinder.require(conversationId);

        return client.prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId.toString()))
                .user(request)
                .stream()
                .content();
    }
}
