package com.github.zjor.telegram.bot.framework.demo.echo.handlers;

import com.github.zjor.telegram.bot.api.dto.Message;
import com.github.zjor.telegram.bot.api.dto.SendMessageRequest;

import java.util.function.Function;

public class DefaultMessageHandler implements Function<Message, SendMessageRequest> {

    @Override
    public SendMessageRequest apply(Message message) {
        return new SendMessageRequest(message.getFrom().getId(), "Sorry, I don't know what to say");
    }
}
