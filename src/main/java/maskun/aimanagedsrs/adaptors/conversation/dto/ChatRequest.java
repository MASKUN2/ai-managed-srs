package maskun.aimanagedsrs.adaptors.conversation.dto;

import javax.validation.constraints.NotNull;

public record ChatRequest(@NotNull String content) {
}
