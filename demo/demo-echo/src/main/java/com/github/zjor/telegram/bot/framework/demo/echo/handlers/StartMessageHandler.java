package com.github.zjor.telegram.bot.framework.demo.echo.handlers;

import com.github.zjor.telegram.bot.api.dto.Message;
import com.github.zjor.telegram.bot.api.dto.User;
import com.github.zjor.telegram.bot.framework.demo.echo.Keyboard;
import com.github.zjor.telegram.bot.framework.dispatch.AbstractMessageHandler;
import com.github.zjor.telegram.bot.framework.dispatch.HandlingFailedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

@Slf4j
public class StartMessageHandler extends AbstractMessageHandler {

    public static final String START_COMMAND = "/start";
    public static final String GREETING_MESSAGE = "Hello {0}! Let's /echo something :)";

    @Override
    public boolean handle(Message message) throws HandlingFailedException {
        String text = message.getText();
        if (StringUtils.isNotEmpty(text) && text.startsWith(START_COMMAND)) {
            User user = message.getFrom();
            String response = MessageFormat.format(GREETING_MESSAGE, user.getFirstName());
            replyWithText(user.getId(), response, Keyboard.KEYBOARD);
            return true;
        }
        return false;
    }
}
