package maskun.aimanagedsrs.adaptors.conversation.api;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.adaptors.conversation.dto.ChatRequest;
import maskun.aimanagedsrs.hexagon.conversation.provided.ChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ChatApi {
    static final String BASE_PATH = ConversationApi.BASE_PATH + "/messages";

    private final ChatService chatService;

    @PostMapping(path = BASE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> getAssistantStreamResponse(@Valid @RequestBody ChatRequest request) {
        return chatService.chat(request.conversationId(), request.content());
    }
}
