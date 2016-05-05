package com.github.zjor.telegram.bot.framework.demo.echo.handlers;

import com.github.zjor.telegram.bot.api.dto.Message;
import com.github.zjor.telegram.bot.api.dto.SendMessageRequest;
import com.github.zjor.telegram.bot.framework.dispatch.HandlingFailedException;

import java.util.function.BiFunction;

public class DefaultErrorHandler implements BiFunction<Message, HandlingFailedException, SendMessageRequest> {

    @Override
    public SendMessageRequest apply(Message message, HandlingFailedException e) {
        return new SendMessageRequest(message.getFrom().getId(), "Failed to process message: " + message.getText() + " with an exception: " + e.getMessage());
    }
}
