package com.github.zjor.telegram.bot.framework.dispatch;

public class HandlingFailedException extends Exception {
    public HandlingFailedException() {
    }

    public HandlingFailedException(String message) {
        super(message);
    }

    public HandlingFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
