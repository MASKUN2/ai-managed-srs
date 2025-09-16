package maskun.aimanagedsrs.hexagon.conversation.domain;

import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ConversationService {

    Flux<String> chat(UUID conversationId, String request);

    /**
     * 이 메소드는 트랜젝션을 가져야합니다.
     */
    void addNewUserMessage(UUID conversationId, String request);

    void addNewAssistantMessage(UUID conversationId, Flux<String> response);
}
