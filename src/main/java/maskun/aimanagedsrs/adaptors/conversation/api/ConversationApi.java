package maskun.aimanagedsrs.adaptors.conversation.api;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.adaptors.conversation.dto.ConversationResponse;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationStarter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static maskun.aimanagedsrs.adaptors.shared.API.PATH.CONVERSATIONS;
import static maskun.aimanagedsrs.adaptors.shared.API.PATH.VERSION_1;

@RequiredArgsConstructor
@RestController
public class ConversationApi {
    static final String BASE_PATH = VERSION_1 + CONVERSATIONS;

    private final ConversationStarter conversationStarter;

    @PostMapping(BASE_PATH)
    public ResponseEntity<ConversationResponse> startNewConversation() {
        Conversation conversation = conversationStarter.startNew();
        ConversationResponse responseBody = ConversationResponse.of(conversation);

        return ResponseEntity.ok(responseBody);
    }
}
