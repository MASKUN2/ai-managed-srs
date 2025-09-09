package maskun.aimanagedsrs.hexagon.chat;

import javax.validation.constraints.NotNull;

public record UserChatRequest(
        @NotNull String message
) {
}
