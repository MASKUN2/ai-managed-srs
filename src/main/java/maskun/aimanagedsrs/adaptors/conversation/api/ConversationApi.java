package maskun.aimanagedsrs.adaptors.conversation.api;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.adaptors.conversation.dto.ChatRequest;
import maskun.aimanagedsrs.adaptors.conversation.dto.ConversationResponse;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.provided.ChatService;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationStarter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ConversationApi {
    static final String BASE_PATH = "/api/v1/conversation";
    static final String MESSAGE_PATH = BASE_PATH + "/messages";

    private final ConversationStarter conversationStarter;
    private final ChatService chatService;

    @PostMapping(BASE_PATH)
    public ResponseEntity<ConversationResponse> startNewConversation() {
        Conversation conversation = conversationStarter.startNew();
        ConversationResponse responseBody = ConversationResponse.of(conversation);

        return ResponseEntity.ok(responseBody);
    }

    @PostMapping(path = MESSAGE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> getAssistantStreamResponse(@Valid @RequestBody ChatRequest request) {
        return chatService.chat(request.conversationId(), request.content());
    }
}
