package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.api.dto.Message;
import com.github.zjor.telegram.bot.api.dto.methods.SendMessage;

import java.util.function.BiFunction;

public class DefaultErrorHandler implements BiFunction<Message, HandlingFailedException, SendMessage> {

    @Override
    public SendMessage apply(Message message, HandlingFailedException e) {
        return new SendMessage(message.getFrom().getId(), "Failed to process message: " + message.getText() + " with an exception: " + e.getMessage());
    }
}
