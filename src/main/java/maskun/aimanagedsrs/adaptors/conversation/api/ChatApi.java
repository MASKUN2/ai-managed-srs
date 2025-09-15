package maskun.aimanagedsrs.adaptors.conversation.api;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.adaptors.conversation.dto.ChatRequest;
import maskun.aimanagedsrs.hexagon.conversation.provided.ChatService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.util.UUID;

import static maskun.aimanagedsrs.adaptors.shared.API.PATH.MESSAGES;

@RequiredArgsConstructor
@RestController
public class ChatApi {
    static final String BASE_PATH = ConversationApi.BASE_PATH + "/{conversationId}" + MESSAGES;

    private final ChatService chatService;

    @PostMapping(path = BASE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> getAssistantStreamResponse(@PathVariable UUID conversationId,
                                                   @Valid @RequestBody ChatRequest request) {
        return chatService.chat(conversationId, request.content());
    }
}
