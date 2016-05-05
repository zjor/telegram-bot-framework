package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.api.dto.SendMessageRequest;

import java.util.List;

public interface MessageHandler {

    List<SendMessageRequest> handle(MessageContext context) throws HandlingFailedException;

}
