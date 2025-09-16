package maskun.aimanagedsrs.hexagon.conversation.domain;

import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConversationTest {

    @Test
    void createNew() {
        Conversation conversation = Conversation.startNew();

        assertThat(conversation).isNotNull();
        assertThat(conversation.getId()).isNotNull();
        assertThat(conversation.getChatMessages()).isEmpty();
        assertThat(conversation.getCreatedAt()).isNotNull();
    }

    @Test
    void appendMessage() {
        Conversation conversation = Conversation.startNew();
        assertThat(conversation.getChatMessages()).isEmpty();

        var message = conversation.append(Conversation.Role.USER, "some content");
        assertThat(conversation.getChatMessages()).hasSize(1);
        assertThat(conversation.getChatMessages().getFirst()).isEqualTo(message);
        assertThat(message).satisfies(msg -> {
            assertThat(msg.getId()).isNotNull();
            assertThat(msg.getConversation()).isEqualTo(conversation);
            assertThat(msg.getRole()).isEqualTo(Conversation.Role.USER);
            assertThat(msg.getContent()).isEqualTo("some content");
            assertThat(msg.getCreatedAt()).isNotNull();
        });

        var otherMessage = conversation.append(Conversation.Role.ASSISTANT, "some other content");
        assertThat(conversation.getChatMessages()).hasSize(2);
        assertThat(conversation.getChatMessages().getLast()).isEqualTo(otherMessage);
        assertThat(otherMessage).satisfies(msg -> {
            assertThat(msg.getId()).isNotNull();
            assertThat(msg.getConversation()).isEqualTo(conversation);
            assertThat(msg.getRole()).isEqualTo(Conversation.Role.ASSISTANT);
            assertThat(msg.getContent()).isEqualTo("some other content");
            assertThat(msg.getCreatedAt()).isNotNull();
        });
    }
}