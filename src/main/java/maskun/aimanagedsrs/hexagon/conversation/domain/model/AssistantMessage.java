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
public class AssistantMessage extends Message<AssistantMessage> {

    public static AssistantMessage of() {
        return new AssistantMessage();
    }
}
