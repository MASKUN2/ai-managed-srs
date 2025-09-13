package maskun.aimanagedsrs.adaptors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class ConversationUi {
    static final String CONVERSATION_URL = "/conversation";
    public static final String CHAT_VIEW_NAME = "conversation";

    @GetMapping(CONVERSATION_URL + "/{conversationId}")
    public String getConversationView(@PathVariable UUID conversationId, Model model) {

        model.addAttribute("conversationId", conversationId);

        return CHAT_VIEW_NAME;
    }
}
