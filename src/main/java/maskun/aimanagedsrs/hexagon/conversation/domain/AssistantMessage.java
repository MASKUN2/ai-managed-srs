package maskun.aimanagedsrs.hexagon.conversation.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("ASSISTANT")
@Entity
public class AssistantMessage extends Message {

    public static AssistantMessage of() {
        return new AssistantMessage();
    }
}
