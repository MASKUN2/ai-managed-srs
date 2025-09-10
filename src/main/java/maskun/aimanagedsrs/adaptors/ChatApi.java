package maskun.aimanagedsrs.adaptors;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.chat.UserChatRequest;
import maskun.aimanagedsrs.hexagon.chat.provided.Chat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ChatApi {
    static final String CHAT_API_URL = "/api/v1/chat";
    final Chat chat;

    @PostMapping(CHAT_API_URL)
    public ResponseEntity<UserChatResponse> send(@Valid @RequestBody UserChatRequest request) {
        return ResponseEntity.ok(new UserChatResponse(chat.send(request)));
    }
}
