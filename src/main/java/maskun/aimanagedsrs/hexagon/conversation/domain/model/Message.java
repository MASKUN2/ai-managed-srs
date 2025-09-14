package maskun.aimanagedsrs.hexagon.conversation.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maskun.aimanagedsrs.hexagon.shared.BaseEntity;
import org.jspecify.annotations.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "message", indexes = @Index(name = "idx_message_created_at", columnList = "created_at"))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role", length = 10)
public abstract class Message extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", foreignKey = @ForeignKey(name = "fk_message_conversation"))
    protected @Nullable Conversation conversation;

    @Column(columnDefinition = "TEXT")
    protected @Nullable String content = null;

    public Message content(@Nullable String content) {
        this.content = content;
        return this;
    }
}

