package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.api.dto.Message;
import com.github.zjor.telegram.bot.framework.model.TelegramUser;
import com.github.zjor.telegram.bot.framework.service.MessageService;
import com.github.zjor.telegram.bot.framework.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

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
        TelegramUser user = userService.ensureExists(message.getFrom());
        List<com.github.zjor.telegram.bot.framework.model.Message> conversation = messageService.last(user, conversationDepth);
        return new MessageContext(user, conversation, messageService.create(message, user));
    }
}
