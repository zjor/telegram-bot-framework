package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.api.dto.Message;
import com.github.zjor.telegram.bot.api.dto.methods.SendMessage;

import java.util.function.Function;

public class DefaultMessageHandler implements Function<Message, SendMessage> {

    @Override
    public SendMessage apply(Message message) {
        return new SendMessage(message.getFrom().getId(), "Sorry, I don't know what to say");
    }
}
