package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.api.dto.Message;
import com.github.zjor.telegram.bot.framework.model.TelegramUser;
import com.github.zjor.telegram.bot.framework.service.MessageService;
import com.github.zjor.telegram.bot.framework.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Slf4j
public class ContextResolver {

    @Inject
    private UserService userService;

    @Inject
    private MessageService messageService;

    private int conversationDepth;

    public ContextResolver(int conversationDepth) {
        this.conversationDepth = conversationDepth;
    }

    @Transactional
    public MessageContext resolve(Message message) {
        try {
            TelegramUser user = userService.ensureExists(message.getFrom());
            List<com.github.zjor.telegram.bot.framework.model.Message> conversation = messageService.last(user, conversationDepth);
            com.github.zjor.telegram.bot.framework.model.Message currentMessage = messageService.create(message, user);

            log.debug("Resolved context for {}", user);
            conversation.stream().forEach(m -> log.debug("Conversation: {}", m.getText()));
            log.debug("Current message: {}", currentMessage.getText());

            return new MessageContext(user, conversation, currentMessage);
        } catch (Throwable t) {
            log.error("Failed to resolve context: " + t.getMessage(), t);
            throw t;
        }
    }
}
