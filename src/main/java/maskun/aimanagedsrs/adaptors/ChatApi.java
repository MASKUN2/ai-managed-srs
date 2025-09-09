package maskun.aimanagedsrs.adaptors;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.chat.provided.Chat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatApi {
    static final String CHAT_API_URL = "/api/v1/chat";
    final Chat chat;

    public ResponseEntity<String> send(String message) {
        return ResponseEntity.ok(chat.send(message));
    }
}
