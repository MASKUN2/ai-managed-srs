package maskun.aimanagedsrs.hexagon.conversation;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record UserChatRequestMessage(
        @NotNull UUID conversationId,
        @NotNull String content) {
}
