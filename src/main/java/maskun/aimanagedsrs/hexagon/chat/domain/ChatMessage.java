package maskun.aimanagedsrs.hexagon.chat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.ai.chat.messages.MessageType;

import java.time.Instant;

@Entity
@Table(name = "chat_message", indexes = @Index(columnList = "conversation_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conversation_id", nullable = false)
    private String conversationId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(columnDefinition = "TEXT")
    private String content;

    public static ChatMessage of(@NonNull String conversationId,
                                 @NonNull String content,
                                 @NonNull MessageType type) {
        var chatMessage = new ChatMessage();
        chatMessage.conversationId = conversationId;
        chatMessage.content = content;
        chatMessage.type = type;
        chatMessage.createdAt = Instant.now();
        return chatMessage;
    }

}
