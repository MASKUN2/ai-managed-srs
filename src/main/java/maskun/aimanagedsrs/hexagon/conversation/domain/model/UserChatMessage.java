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
public class UserChatMessage extends ChatMessage<UserChatMessage> {

    public static UserChatMessage of() {
        return new UserChatMessage();
    }
}
