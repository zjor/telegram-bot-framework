package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.api.Telegram;
import com.github.zjor.telegram.bot.api.dto.ReplyKeyboardMarkup;
import com.github.zjor.telegram.bot.api.dto.SendMessageRequest;

import javax.inject.Inject;

public abstract class AbstractMessageHandler implements MessageHandler {

    @Inject
    private Telegram telegram;

    protected void replyWithText(int userId, String text) {
        telegram.sendMessage(new SendMessageRequest(userId, text));
    }

    protected void replyWithText(int userId, String text, ReplyKeyboardMarkup keyboard) {
        SendMessageRequest req = new SendMessageRequest(userId, text);
        req.setReplyMarkup(keyboard);
        telegram.sendMessage(req);
    }

    protected void replyWithText(int userId, String text, String parseMode, ReplyKeyboardMarkup keyboard) {
        SendMessageRequest req = new SendMessageRequest(userId, text, parseMode);
        req.setReplyMarkup(keyboard);
        req.setDisableWebPageView(true);
        telegram.sendMessage(req);
    }
}
