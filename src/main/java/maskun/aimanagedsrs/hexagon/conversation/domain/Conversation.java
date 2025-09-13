package maskun.aimanagedsrs.hexagon.conversation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maskun.aimanagedsrs.hexagon.shared.UUIDv7;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public Message addNewMessage(Message.Role role, String content) {
        Message message = Message.of(this, role, content);
        messages.add(message);
        return message;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Conversation that = (Conversation) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "createdAt = " + createdAt + ")";
    }
}
