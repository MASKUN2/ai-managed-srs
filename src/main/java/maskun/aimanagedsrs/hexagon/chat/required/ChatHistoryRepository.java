package maskun.aimanagedsrs.hexagon.chat.required;

import maskun.aimanagedsrs.hexagon.chat.domain.ChatMessage;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ChatHistoryRepository extends Repository<ChatMessage, Long> {
    ChatMessage save(ChatMessage message);

    List<ChatMessage> findAllByConversationIdOrderByCreatedAtDesc(String conversationId);

    void deleteAllByConversationId(String conversationId);
}