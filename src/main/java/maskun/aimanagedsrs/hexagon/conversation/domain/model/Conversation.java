package maskun.aimanagedsrs.hexagon.conversation.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maskun.aimanagedsrs.hexagon.shared.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Conversation extends BaseEntity {

    @OrderBy("createdAt ASC")
    @OneToMany(mappedBy = "conversation", cascade = {PERSIST, MERGE, REFRESH})
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public static Conversation startNew() {
        return new Conversation();
    }

    public ChatMessage append(ChatMessage message) {
        message.conversation = this;
        chatMessages.add(message);
        return message;
    }
}
