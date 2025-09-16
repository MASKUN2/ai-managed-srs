package maskun.aimanagedsrs.hexagon.conversation.domain;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.AssistantMessage;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.Conversation;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserMessage;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatHistoryImpl implements ChatHistory {
    private final ConversationFinder conversationFinder;
    private final ConversationRepository conversationRepository;

    @Override
    @Transactional
    public void add(String conversationId, List<Message> messages) {
        Conversation conversation = conversationFinder.require(UUID.fromString(conversationId));

        for (var message : messages) {
            conversation.append(MapToMessage(message));
        }
        conversationRepository.save(conversation);

    }

    private maskun.aimanagedsrs.hexagon.conversation.domain.model.Message MapToMessage(Message message) {
        MessageType messageType = message.getMessageType();

        maskun.aimanagedsrs.hexagon.conversation.domain.model.Message msg = switch (messageType) {
            case USER -> UserMessage.of();
            case ASSISTANT -> AssistantMessage.of();
            default -> throw new IllegalArgumentException("Unsupported message type: " + messageType);
        };

        msg.content(message.getText());

        return msg;
    }
}
