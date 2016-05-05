package com.github.zjor.telegram.bot.framework.demo.echo.handlers;

import com.github.zjor.telegram.bot.api.dto.SendMessageRequest;
import com.github.zjor.telegram.bot.framework.demo.echo.Keyboard;
import com.github.zjor.telegram.bot.framework.dispatch.HandlingFailedException;
import com.github.zjor.telegram.bot.framework.dispatch.MessageContext;
import com.github.zjor.telegram.bot.framework.dispatch.MessageHandler;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

public class EchoMessageHandler implements MessageHandler {

    public static final String ECHO_COMMAND = "/echo";

    @Override
    public List<SendMessageRequest> handle(MessageContext context) throws HandlingFailedException {
        String text = context.getCurrentMessage().getText();
        if (StringUtils.isNoneEmpty(text) && text.startsWith(ECHO_COMMAND)) {

            SendMessageRequest req = new SendMessageRequest(context.getUser().getTelegramId(), text.substring(ECHO_COMMAND.length()).trim());
            req.setReplyMarkup(Keyboard.KEYBOARD);

            return Collections.singletonList(req);
        }
        return Collections.emptyList();
    }
}
