package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.api.Telegram;
import com.github.zjor.telegram.bot.api.TelegramException;
import com.github.zjor.telegram.bot.api.dto.InlineQuery;
import com.github.zjor.telegram.bot.api.dto.Message;
import com.github.zjor.telegram.bot.api.dto.Update;
import com.github.zjor.telegram.bot.api.dto.methods.SendMessage;
import com.github.zjor.telegram.bot.framework.service.MessageService;
import com.github.zjor.telegram.bot.framework.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Applies handlers until first successful handling
 */
@Slf4j
public class UpdateHandler {

    @Inject
    private ExecutorService executorService;

    @Inject
    private Telegram telegram;

    @Inject
    private UserService userService;

    @Inject
    private MessageService messageService;

    private List<MessageHandler> handlers;

    private Consumer<InlineQuery> inlineQueryHandler;

    private Function<Message, SendMessage> defaultMessageHandler = new DefaultMessageHandler();

    private BiFunction<Message, HandlingFailedException, SendMessage> defaultErrorHandler = new DefaultErrorHandler();

    public UpdateHandler(List<MessageHandler> handlers) {
        this.handlers = handlers;
    }

    public UpdateHandler(List<MessageHandler> handlers, Consumer<InlineQuery> inlineQueryHandler) {
        this.handlers = handlers;
        this.inlineQueryHandler = inlineQueryHandler;
    }

    public void handle(final Update update) {
        executorService.submit(() -> {
            Optional.ofNullable(update.getMessage()).ifPresent(message -> {
                log.info("<= {}", message);
                messageService.create(message, userService.ensureExists(message.getFrom()));

                try {
                    Iterator<MessageHandler> it = handlers.iterator();
                    boolean handled = false;
                    while (it.hasNext() && !handled) {
                        handled = handled || it.next().handle(message);
                    }
                    if (!handled) {
                        telegram.sendMessage(defaultMessageHandler.apply(message));
                    }
                } catch (HandlingFailedException e) {
                    log.error("Handling failed: " + e.getMessage(), e);
                    telegram.sendMessage(defaultErrorHandler.apply(message, e));
                } catch (Throwable t) {
                    log.error("WTF: " + t.getMessage(), t);
                }

            });
            Optional.ofNullable(update.getInlineQuery()).ifPresent(inlineQuery -> {
                log.info("inlineQuery: {}", inlineQuery);
                userService.ensureExists(inlineQuery.getFrom());
                if (inlineQueryHandler != null) {
                    inlineQueryHandler.accept(inlineQuery);
                }
            });
        });
    }

    private void sendSilent(SendMessage req) {
        try {
            telegram.sendMessage(req);
        } catch (TelegramException e) {
            log.error("Sending failed: " + e.getMessage(), e);
        }
    }

}
