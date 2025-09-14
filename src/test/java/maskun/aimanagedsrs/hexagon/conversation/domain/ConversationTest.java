package maskun.aimanagedsrs.hexagon.conversation.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConversationTest {

    @Test
    void createNew() {
        Conversation conversation = Conversation.startNew();

        assertThat(conversation).isNotNull();
        assertThat(conversation.getId()).isNotNull();
        assertThat(conversation.getMessages()).isEmpty();
        assertThat(conversation.getCreatedAt()).isNotNull();
    }

    @Test
    void appendMessage() {
        Conversation conversation = Conversation.startNew();
        assertThat(conversation.getMessages()).isEmpty();

        var message = conversation.append(Conversation.Role.USER, "some content");
        assertThat(conversation.getMessages()).hasSize(1);
        assertThat(conversation.getMessages().getFirst()).isEqualTo(message);
        assertThat(message).satisfies(msg -> {
            assertThat(msg.getId()).isNotNull();
            assertThat(msg.getConversation()).isEqualTo(conversation);
            assertThat(msg.getRole()).isEqualTo(Conversation.Role.USER);
            assertThat(msg.getContent()).isEqualTo("some content");
            assertThat(msg.getCreatedAt()).isNotNull();
        });

        var otherMessage = conversation.append(Conversation.Role.ASSISTANT, "some other content");
        assertThat(conversation.getMessages()).hasSize(2);
        assertThat(conversation.getMessages().getLast()).isEqualTo(otherMessage);
        assertThat(otherMessage).satisfies(msg -> {
            assertThat(msg.getId()).isNotNull();
            assertThat(msg.getConversation()).isEqualTo(conversation);
            assertThat(msg.getRole()).isEqualTo(Conversation.Role.ASSISTANT);
            assertThat(msg.getContent()).isEqualTo("some other content");
            assertThat(msg.getCreatedAt()).isNotNull();
        });
    }
}