package maskun.aimanagedsrs.hexagon.conversation;

import java.util.UUID;

public record MessageRequest(
        UUID conversationId
        , String content
) {
}
