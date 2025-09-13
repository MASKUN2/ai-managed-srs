package maskun.aimanagedsrs.adaptors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConversationUi {
    public static final String CHAT_VIEW_NAME = "conversation";
    static final String CONVERSATION_URL = "/conversation";

    @GetMapping(CONVERSATION_URL)
    public String getConversationView() {
        return CHAT_VIEW_NAME;
    }
}
