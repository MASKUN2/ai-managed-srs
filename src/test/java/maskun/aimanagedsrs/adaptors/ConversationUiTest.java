package maskun.aimanagedsrs.adaptors;

import maskun.aimanagedsrs.adaptors.conversation.ui.ConversationUi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(ConversationUi.class)
class ConversationUiTest {

    @Autowired
    MockMvcTester tester;

    @Test
    void getConversationView() {
        tester.get()
                .uri(ConversationUi.CONVERSATION_URL)
                .assertThat()
                .hasStatusOk()
                .hasViewName(ConversationUi.CHAT_VIEW_NAME);
    }
}