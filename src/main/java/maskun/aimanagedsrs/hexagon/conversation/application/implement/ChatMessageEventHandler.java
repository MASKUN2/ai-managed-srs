package maskun.aimanagedsrs.hexagon.conversation.application.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.application.ChatMessageRecorder;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageEventHandler implements ChatMessageRecorder {
    private final ConversationFinder conversationFinder;
    private final ConversationRepository conversationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void record(String conversationId, List<Message> messages) {
        for (var message : messages) {
            eventPublisher.publishEvent(new ChatMessageAddEvent(conversationId, message));
        }
    }

    @Async
    @Transactional
    @EventListener
    public void onAdd(ChatMessageAddEvent event) {
        try {
            var conversation = conversationFinder.require(UUID.fromString(event.conversationId()));

            conversation.append(MapToMessage(event.message()));

            conversationRepository.save(conversation);
        } catch (Exception e) {
            log.error("대화메세지를 기록하는데 실패했습니다. ChatMessageAddEvent: {}", event, e);
        }
    }

    private ChatMessage MapToMessage(Message message) {
        MessageType messageType = message.getMessageType();

        ChatMessage msg = switch (messageType) {
            case USER -> UserChatMessage.of();
            case ASSISTANT -> AssistantChatMessage.of();
            default -> throw new IllegalArgumentException("지원되지 않는 메시지 유형 : " + messageType);
        };

        msg.content(message.getText());

        return msg;
    }
}

