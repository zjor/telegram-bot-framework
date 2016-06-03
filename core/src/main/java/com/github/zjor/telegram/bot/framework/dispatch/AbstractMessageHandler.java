package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.api.Telegram;
import com.github.zjor.telegram.bot.api.dto.ReplyKeyboardMarkup;
import com.github.zjor.telegram.bot.api.dto.methods.SendMessage;
import lombok.AccessLevel;
import lombok.Getter;

import javax.inject.Inject;

public abstract class AbstractMessageHandler implements MessageHandler {

    @Inject
    @Getter(AccessLevel.PROTECTED)
    private Telegram telegram;

    protected void replyWithText(int userId, String text) {
        telegram.sendMessage(new SendMessage(userId, text));
    }

    protected void replyWithText(int userId, String text, ReplyKeyboardMarkup keyboard) {
        SendMessage req = new SendMessage(userId, text);
        req.setReplyMarkup(keyboard);
        telegram.sendMessage(req);
    }

    protected void replyWithText(int userId, String text, String parseMode, ReplyKeyboardMarkup keyboard) {
        SendMessage req = new SendMessage(userId, text, parseMode);
        req.setReplyMarkup(keyboard);
        req.setDisableWebPageView(true);
        telegram.sendMessage(req);
    }
}
