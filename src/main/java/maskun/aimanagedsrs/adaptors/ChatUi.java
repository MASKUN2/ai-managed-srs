package maskun.aimanagedsrs.adaptors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatUi {
    static final String CHAT_URL = "/chat";
    public static final String CHAT_VIEW_NAME = "chat";

    @GetMapping(CHAT_URL)
    public String chat() {
        return CHAT_VIEW_NAME;
    }
}
