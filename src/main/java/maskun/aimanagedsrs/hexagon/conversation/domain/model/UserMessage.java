package maskun.aimanagedsrs.hexagon.conversation.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("USER")
@Entity
public class UserMessage extends Message {

    public static UserMessage of() {
        return new UserMessage();
    }
}
