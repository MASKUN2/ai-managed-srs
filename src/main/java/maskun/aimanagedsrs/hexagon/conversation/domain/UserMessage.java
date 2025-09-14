package maskun.aimanagedsrs.hexagon.conversation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserMessage extends Message {

    @Column(name = "role", nullable = false, updatable = false, insertable = false)
    @Enumerated(EnumType.STRING)
    private Conversation.Role role;

    private UserMessage(Conversation conversation) {
        super(conversation);
    }

    static UserMessage of(Conversation conversation
    ) {
        return new UserMessage(conversation);
    }

}
