package maskun.aimanagedsrs.hexagon.conversation.application;


import org.springframework.ai.chat.client.ChatClientMessageAggregator;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.ArrayList;
import java.util.List;

public final class ChatHistoryAdvisor implements BaseChatMemoryAdvisor {

    private final ChatMessageEventPublisher chatMessageEventPublisher;

    private final String defaultConversationId;

    private final int order;

    private final Scheduler scheduler;

    private ChatHistoryAdvisor(ChatMessageEventPublisher chatMessageEventPublisher, String defaultConversationId, int order,
                               Scheduler scheduler) {
        Assert.notNull(chatMessageEventPublisher, ChatMessageEventPublisher.class.getSimpleName() + " cannot be null");
        Assert.hasText(defaultConversationId, "defaultConversationId cannot be null or empty");
        Assert.notNull(scheduler, "scheduler cannot be null");
        this.chatMessageEventPublisher = chatMessageEventPublisher;
        this.defaultConversationId = defaultConversationId;
        this.order = order;
        this.scheduler = scheduler;
    }

    public static Builder builder(ChatMessageEventPublisher chatMessageEventPublisher) {
        return new Builder(chatMessageEventPublisher);
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
        this.chatMessageEventPublisher.chatMessageAddEvent(conversationId, userMessage);

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
        this.chatMessageEventPublisher.chatMessageAddEvent(this.getConversationId(chatClientResponse.context(), this.defaultConversationId),
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

        private final ChatMessageEventPublisher chatMessageEventPublisher;
        private String conversationId = ChatMessageEventPublisher.DEFAULT_CONVERSATION_ID;
        private int order = Advisor.DEFAULT_CHAT_MEMORY_PRECEDENCE_ORDER + 1;
        private Scheduler scheduler = BaseAdvisor.DEFAULT_SCHEDULER;

        private Builder(ChatMessageEventPublisher chatMessageEventPublisher) {
            this.chatMessageEventPublisher = chatMessageEventPublisher;
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

        public ChatHistoryAdvisor build() {
            return new ChatHistoryAdvisor(this.chatMessageEventPublisher, this.conversationId, this.order, this.scheduler);
        }

    }

}
