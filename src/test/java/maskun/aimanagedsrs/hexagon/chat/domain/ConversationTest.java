package maskun.aimanagedsrs.hexagon.chat.domain;

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
    void addNewMessage() {
        Conversation conversation = Conversation.startNew();
        assertThat(conversation.getMessages()).isEmpty();

        var message = conversation.addNewMessage(Message.Role.USER, "some message");
        assertThat(conversation.getMessages()).hasSize(1);
        assertThat(conversation.getMessages().getFirst()).isEqualTo(message);
        assertThat(message).satisfies(msg -> {
            assertThat(msg.getId()).isNotNull();
            assertThat(msg.getConversation()).isEqualTo(conversation);
            assertThat(msg.getRole()).isEqualTo(Message.Role.USER);
            assertThat(msg.getContent()).isEqualTo("some message");
            assertThat(msg.getCreatedAt()).isNotNull();
        });

        var otherMessage = conversation.addNewMessage(Message.Role.ASSISTANT, "some other message");
        assertThat(conversation.getMessages()).hasSize(2);
        assertThat(conversation.getMessages().getLast()).isEqualTo(otherMessage);
        assertThat(otherMessage).satisfies(msg -> {
            assertThat(msg.getId()).isNotNull();
            assertThat(msg.getConversation()).isEqualTo(conversation);
            assertThat(msg.getRole()).isEqualTo(Message.Role.ASSISTANT);
            assertThat(msg.getContent()).isEqualTo("some other message");
            assertThat(msg.getCreatedAt()).isNotNull();
        });
    }
}