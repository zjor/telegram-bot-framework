package com.github.zjor.telegram.bot.framework.demo.echo.handlers;

import com.github.zjor.telegram.bot.api.dto.SendMessageRequest;
import com.github.zjor.telegram.bot.framework.dispatch.HandlingFailedException;
import com.github.zjor.telegram.bot.framework.dispatch.MessageContext;
import com.github.zjor.telegram.bot.framework.dispatch.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@Slf4j
public class StartMessageHandler implements MessageHandler {

    public static final String START_COMMAND = "/start";
    public static final String GREETING_MESSAGE = "Hello {0}! Let's /echo something :)";

    @Override
    public List<SendMessageRequest> handle(MessageContext context) throws HandlingFailedException {
        String text = context.getCurrentMessage().getText();
        if (StringUtils.isNotEmpty(text) && text.startsWith(START_COMMAND)) {
            String response = MessageFormat.format(GREETING_MESSAGE, context.getUser().getFirstName());

            return Collections.singletonList(new SendMessageRequest(context.getUser().getTelegramId(), response));
        }

        return Collections.emptyList();
    }
}
