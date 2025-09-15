package maskun.aimanagedsrs.adaptors.conversation.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record ChatRequest(
        @NotNull UUID conversationId,
        @NotNull String content) {
}
