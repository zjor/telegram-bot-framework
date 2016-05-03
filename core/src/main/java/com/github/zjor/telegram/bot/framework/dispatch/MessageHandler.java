package com.github.zjor.telegram.bot.framework.dispatch;

import java.util.Optional;

public interface MessageHandler {

    Optional<Result> handle(MessageContext context) throws HandlingFailedException;

}
