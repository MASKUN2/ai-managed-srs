package maskun.aimanagedsrs.hexagon.conversation.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("ASSISTANT")
@Entity
public class AssistantChatMessage extends ChatMessage {

    public static AssistantChatMessage of() {
        return new AssistantChatMessage();
    }
}
