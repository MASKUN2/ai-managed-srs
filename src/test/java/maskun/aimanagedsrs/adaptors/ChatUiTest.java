package maskun.aimanagedsrs.adaptors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(ChatUi.class)
class ChatUiTest {

    @Autowired
    MockMvcTester tester;

    @Test
    void chat() {
        tester.get()
                .uri(ChatUi.CHAT_URL)
                .assertThat()
                .hasStatusOk()
                .hasViewName(ChatUi.CHAT_VIEW_NAME);
    }
}