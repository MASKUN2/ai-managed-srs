package maskun.aimanagedsrs.hexagon.conversation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssistantMessage extends Message {

    @Column(name = "role", nullable = false, updatable = false, insertable = false)
    @Enumerated(EnumType.STRING)
    private Conversation.Role role;


    protected static AssistantMessage of(Conversation conversation
    ) {
        var message = new AssistantMessage();
        message.conversation = conversation;
        return message;
    }
}
