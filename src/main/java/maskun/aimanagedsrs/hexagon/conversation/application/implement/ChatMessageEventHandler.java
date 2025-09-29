package maskun.aimanagedsrs.hexagon.conversation.application.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maskun.aimanagedsrs.hexagon.conversation.application.ChatMessageRecorder;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.AssistantChatMessage;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.ChatMessage;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.ChatRole;
import maskun.aimanagedsrs.hexagon.conversation.domain.model.UserChatMessage;
import maskun.aimanagedsrs.hexagon.conversation.provided.ConversationFinder;
import maskun.aimanagedsrs.hexagon.conversation.required.ConversationRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatMessageEventHandler implements ChatMessageRecorder {
    private final ConversationFinder conversationFinder;
    private final ConversationRepository conversationRepository;
    private final ApplicationEventPublisher eventPublisher;

    private static ChatMessage buildChatMessage(ChatMessageAddEvent event) {
        ChatMessage message = switch (event.chatRole()) {
            case USER -> UserChatMessage.of();
            case ASSISTANT -> AssistantChatMessage.of();
        };

        return message.content(event.content());
    }

    @Override
    public void record(UUID conversationId, ChatRole chatRole, String content) {
        eventPublisher.publishEvent(new ChatMessageAddEvent(conversationId, chatRole, content));
    }

    @Async
    @Transactional
    @EventListener
    public void onAdd(ChatMessageAddEvent event) {

        try {
            var conversation = conversationFinder.require(event.conversationId());

            ChatMessage message = buildChatMessage(event);
            conversation.append(message);

            conversationRepository.save(conversation);
        } catch (Exception e) {
            log.error("메세지 기록에 실패하였습니다. {}", event, e);
        }

    }

    public record ChatMessageAddEvent(UUID conversationId, ChatRole chatRole, String content) {
    }
}

