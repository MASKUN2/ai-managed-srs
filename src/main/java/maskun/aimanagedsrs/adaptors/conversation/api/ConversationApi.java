package maskun.aimanagedsrs.adaptors.conversation.api;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.adaptors.conversation.dto.ConversationResponse;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationStarter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ConversationApi {
    static final String BASE_PATH = "/api/v1/conversation";

    private final ConversationStarter conversationStarter;

    @PostMapping(BASE_PATH)
    public ResponseEntity<ConversationResponse> startNewConversation() {
        Conversation conversation = conversationStarter.startNew();
        ConversationResponse responseBody = ConversationResponse.of(conversation);

        return ResponseEntity.ok(responseBody);
    }
}
