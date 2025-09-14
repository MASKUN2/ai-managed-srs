package maskun.aimanagedsrs.hexagon.conversation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "message", indexes = @Index(name = "idx_message_created_at", columnList = "created_at"))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", length = 10)
public abstract class Message extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", updatable = false, foreignKey = @ForeignKey(name = "fk_message_conversation"))
    protected Conversation conversation;

    @Column(columnDefinition = "TEXT", nullable = false)
    protected String content = "";

    protected Message(Conversation conversation) {
        this.conversation = conversation;
    }
}

