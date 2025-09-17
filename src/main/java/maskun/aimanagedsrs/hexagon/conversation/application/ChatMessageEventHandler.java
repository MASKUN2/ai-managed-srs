package maskun.aimanagedsrs.hexagon.conversation.application;

import lombok.RequiredArgsConstructor;
import maskun.aimanagedsrs.hexagon.conversation.application.dto.ChatMessageAddEvent;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.AssistantChatMessage;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.ChatMessage;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserChatMessage;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ChatMessageEventHandler implements ChatMessageEventPublisher {
    private final ConversationFinder conversationFinder;
    private final ConversationRepository conversationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void add(String conversationId, List<Message> messages) {
        for (var message : messages) {
            eventPublisher.publishEvent(new ChatMessageAddEvent(conversationId, message));
        }
    }

    @Async
    @Transactional
    @EventListener
    public void onAdd(ChatMessageAddEvent event) {
        var conversation = conversationFinder.require(UUID.fromString(event.conversationId()));
        conversation.append(MapToMessage(event.message()));
        conversationRepository.save(conversation);
    }

    private ChatMessage MapToMessage(Message message) {
        MessageType messageType = message.getMessageType();

        ChatMessage msg = switch (messageType) {
            case USER -> UserChatMessage.of();
            case ASSISTANT -> AssistantChatMessage.of();
            default -> throw new IllegalArgumentException("Unsupported message type: " + messageType);
        };

        msg.content(message.getText());

        return msg;
    }
}

