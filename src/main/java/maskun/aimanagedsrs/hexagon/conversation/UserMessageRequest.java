package maskun.aimanagedsrs.hexagon.conversation;

import java.util.UUID;

public record UserMessageRequest(
        UUID conversationId
        , String content
) {
}
