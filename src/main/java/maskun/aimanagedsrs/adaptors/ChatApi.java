package maskun.aimanagedsrs.adaptors;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.chat.UserChatRequest;
import maskun.aimanagedsrs.hexagon.chat.provided.Chat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ChatApi {
    static final String CHAT_API_URL = "/api/v1/chat";
    final Chat chat;

    @PostMapping(
            path = CHAT_API_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public Flux<String> send(@Valid @RequestBody UserChatRequest request) {
        return chat.send(request);
    }
}
