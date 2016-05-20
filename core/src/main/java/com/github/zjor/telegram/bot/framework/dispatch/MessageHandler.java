package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.api.dto.Message;

@FunctionalInterface
public interface MessageHandler {

    /**
     * Handles incoming message
     * @param message
     * @return true if message was handled
     * @throws HandlingFailedException
     */
    boolean handle(Message message) throws HandlingFailedException;

}
