package maskun.aimanagedsrs.hexagon.conversation;

import javax.validation.constraints.NotNull;

public record UserChatRequest(
        @NotNull String message
) {
}
