package maskun.aimanagedsrs.adaptors;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.UserChatRequest;
import maskun.aimanagedsrs.hexagon.conversation.provided.Conversation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ConversationApi {
    static final String API_URL = "/api/v1/conversation";
    final Conversation conversation;

    @PostMapping(
            path = API_URL,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public Flux<String> send(@Valid @RequestBody UserChatRequest request) {
        return conversation.send(request);
    }
}
