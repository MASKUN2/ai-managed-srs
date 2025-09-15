package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.domain.ChatAssistant;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserMessage;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatAssistantClient implements ChatAssistant {

    private final ChatClient client;

    @Override
    public Flux<String> response(Conversation conversation, UserMessage message) {
        return client.prompt()
                .user(message.getContent())
                .stream()
                .content();
    }
}
