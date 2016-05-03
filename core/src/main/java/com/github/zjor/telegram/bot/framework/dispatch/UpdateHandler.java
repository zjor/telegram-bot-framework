package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.api.Telegram;
import com.github.zjor.telegram.bot.api.TelegramException;
import com.github.zjor.telegram.bot.api.dto.Message;
import com.github.zjor.telegram.bot.api.dto.SendMessageRequest;
import com.github.zjor.telegram.bot.api.dto.Update;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
public class UpdateHandler {

    @Inject
    private ContextResolver contextResolver;

    @Inject
    private ExecutorService executorService;

    @Inject
    private Telegram telegram;

    private List<MessageHandler> handlers;

    private Function<Message, SendMessageRequest> defaultMessageHandler;

    private BiFunction<Message, HandlingFailedException, SendMessageRequest> defaultErrorHandler;

    public UpdateHandler(List<MessageHandler> handlers, Function<Message, SendMessageRequest> defaultMessageHandler, BiFunction<Message, HandlingFailedException, SendMessageRequest> defaultErrorHandler) {
        this.handlers = handlers;
        this.defaultMessageHandler = defaultMessageHandler;
        this.defaultErrorHandler = defaultErrorHandler;
    }

    public void handle(Update update) {
        executorService.submit(() -> {
            Message message = update.getMessage();
            log.info("<= {}", message);

            try {
                if (!handleMessage(message)) {
                    send(defaultMessageHandler.apply(message));
                }
            } catch (HandlingFailedException e) {
                log.error("Handling failed: " + e.getMessage(), e);
                send(defaultErrorHandler.apply(message, e));
            }
        });
    }

    private boolean handleMessage(Message message) throws HandlingFailedException {
        MessageContext context = contextResolver.resolve(message);

        //TODO: handlers.map(h -> h.handle(context)).filter(not null).first().match(Success.class -> ..., Failure.class -> ....)
        for (MessageHandler handler : handlers) {
            Optional<Result> resultOpt = handler.handle(context);
            if (resultOpt.isPresent()) {
                Result result = resultOpt.get();
                result.getResult().stream().forEach(this::send);
                return true;
            }
        }
        return false;

    }

    private void send(SendMessageRequest req) {
        try {
            telegram.sendMessage(req);
        } catch (TelegramException e) {
            log.error("Failed to send message: " + req, e);
        }
    }

}
