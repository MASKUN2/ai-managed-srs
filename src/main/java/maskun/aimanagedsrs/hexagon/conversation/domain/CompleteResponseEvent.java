package maskun.aimanagedsrs.hexagon.conversation.domain;

import java.util.UUID;

public record CompleteResponseEvent(
        UUID conversationId
        , String content
) {
}
