package maskun.aimanagedsrs;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ChatClientTest {

    @Autowired
    private ChatClient.Builder builder;

    @Test
    void connectionTest(){
        ChatClient client = builder.build();
        String answer = client.prompt("안녕?")
                .call()
                .content();

        System.out.println("AI:" + answer);

        assertThat(answer).isNotNull();
    }
}
