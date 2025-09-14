package maskun.aimanagedsrs.hexagon.conversation.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserMessage;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssistantClientImpl implements AssistantClient {

    private final ChatClient client;

    @Override
    public Flux<String> getStreamResponse(Conversation conversation, UserMessage message) {
        return client.prompt()
                .user(message.getContent())
                .stream()
                .content();
    }
}
