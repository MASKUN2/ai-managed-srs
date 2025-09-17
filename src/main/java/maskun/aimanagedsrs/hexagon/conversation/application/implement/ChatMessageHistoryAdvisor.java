package maskun.aimanagedsrs.hexagon.conversation.application.implement;


import maskun.aimanagedsrs.hexagon.conversation.application.ChatMessageRecorder;
import org.springframework.ai.chat.client.ChatClientMessageAggregator;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * 주고 받는 채팅을 기록하고 추가된 메세지에 대한 기록을 수행합니다.
 */
public final class ChatMessageHistoryAdvisor implements BaseChatMemoryAdvisor {

    private final ChatMessageRecorder chatMessageRecorder;

    private final String defaultConversationId;

    private final int order;

    private final Scheduler scheduler;

    private ChatMessageHistoryAdvisor(ChatMessageRecorder chatMessageRecorder, String defaultConversationId, int order,
                                      Scheduler scheduler) {
        Assert.notNull(chatMessageRecorder, ChatMessageRecorder.class.getSimpleName() + " cannot be null");
        Assert.hasText(defaultConversationId, "defaultConversationId cannot be null or empty");
        Assert.notNull(scheduler, "scheduler cannot be null");
        this.chatMessageRecorder = chatMessageRecorder;
        this.defaultConversationId = defaultConversationId;
        this.order = order;
        this.scheduler = scheduler;
    }

    public static Builder builder(ChatMessageRecorder chatMessageRecorder) {
        return new Builder(chatMessageRecorder);
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    @Override
    public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
        String conversationId = getConversationId(chatClientRequest.context(), this.defaultConversationId);

        UserMessage userMessage = chatClientRequest.prompt().getUserMessage();
        this.chatMessageRecorder.record(conversationId, userMessage);

        return chatClientRequest;
    }

    @Override
    public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
        List<Message> assistantMessages = new ArrayList<>();
        if (chatClientResponse.chatResponse() != null) {
            assistantMessages = chatClientResponse.chatResponse()
                    .getResults()
                    .stream()
                    .map(g -> (Message) g.getOutput())
                    .toList();
        }
        this.chatMessageRecorder.record(this.getConversationId(chatClientResponse.context(), this.defaultConversationId),
                assistantMessages);
        return chatClientResponse;
    }

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest,
                                                 StreamAdvisorChain streamAdvisorChain) {
        // Get the scheduler from BaseAdvisor
        Scheduler scheduler = this.getScheduler();

        // Process the request with the before method
        return Mono.just(chatClientRequest)
                .publishOn(scheduler)
                .map(request -> this.before(request, streamAdvisorChain))
                .flatMapMany(streamAdvisorChain::nextStream)
                .transform(flux -> new ChatClientMessageAggregator().aggregateChatClientResponse(flux,
                        response -> this.after(response, streamAdvisorChain)));
    }

    public static final class Builder {

        private final ChatMessageRecorder chatMessageRecorder;
        private String conversationId = ChatMemory.CONVERSATION_ID;
        private int order = Advisor.DEFAULT_CHAT_MEMORY_PRECEDENCE_ORDER + 1;
        private Scheduler scheduler = BaseAdvisor.DEFAULT_SCHEDULER;

        private Builder(ChatMessageRecorder chatMessageRecorder) {
            this.chatMessageRecorder = chatMessageRecorder;
        }

        public Builder conversationId(String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public Builder order(int order) {
            this.order = order;
            return this;
        }

        public Builder scheduler(Scheduler scheduler) {
            this.scheduler = scheduler;
            return this;
        }

        public ChatMessageHistoryAdvisor build() {
            return new ChatMessageHistoryAdvisor(this.chatMessageRecorder, this.conversationId, this.order, this.scheduler);
        }

    }

}
