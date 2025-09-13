package maskun.aimanagedsrs.hexagon.chat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import maskun.aimanagedsrs.hexagon.UUIDv7;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conversation {

    @Id
    private UUID id = UUIDv7.generate();

    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "conversation", cascade = {PERSIST, MERGE, REFRESH})
    private List<Message> messages = new ArrayList<>();

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    public static Conversation startNew() {
        return new Conversation();
    }

    public Message addNewMessage(@NonNull Message.Role role, @NonNull String content) {
        Message message = Message.of(this, role, content);
        messages.add(message);
        return message;
    }

}
