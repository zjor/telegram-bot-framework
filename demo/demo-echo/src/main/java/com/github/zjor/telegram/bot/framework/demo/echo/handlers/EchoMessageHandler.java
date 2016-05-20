package com.github.zjor.telegram.bot.framework.demo.echo.handlers;

import com.github.zjor.telegram.bot.api.dto.Message;
import com.github.zjor.telegram.bot.api.dto.User;
import com.github.zjor.telegram.bot.framework.demo.echo.Keyboard;
import com.github.zjor.telegram.bot.framework.dispatch.AbstractMessageHandler;
import com.github.zjor.telegram.bot.framework.dispatch.HandlingFailedException;
import org.apache.commons.lang3.StringUtils;

public class EchoMessageHandler extends AbstractMessageHandler {

    public static final String ECHO_COMMAND = "/echo";

    @Override
    public boolean handle(Message message) throws HandlingFailedException {
        String text = message.getText();
        if (StringUtils.isNoneEmpty(text) && text.startsWith(ECHO_COMMAND)) {
            User user = message.getFrom();
            replyWithText(user.getId(), text.substring(ECHO_COMMAND.length()).trim(), Keyboard.KEYBOARD);
            return true;
        }

        return false;
    }
}
