package maskun.aimanagedsrs.hexagon.chat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import maskun.aimanagedsrs.hexagon.UUIDv7;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "message",
        indexes = @Index(name = "idx_message_created_at", columnList = "created_at"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {

    @Id
    private UUID id = UUIDv7.generate();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", updatable = false, foreignKey = @ForeignKey(name = "fk_message_conversation"))
    private Conversation conversation;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content = "";

    protected static Message of(
            @NonNull Conversation conversation
            , @NonNull Message.Role role
            , @NonNull String content
    ) {
        var message = new Message();
        message.conversation = conversation;
        message.role = role;
        message.content = content;
        return message;
    }

    public enum Role {
        USER,
        ASSISTANT;
    }
}
