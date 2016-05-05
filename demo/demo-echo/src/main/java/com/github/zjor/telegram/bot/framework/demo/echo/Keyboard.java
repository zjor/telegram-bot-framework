package com.github.zjor.telegram.bot.framework.demo.echo;

import com.github.zjor.telegram.bot.api.dto.KeyboardButton;
import com.github.zjor.telegram.bot.api.dto.ReplyKeyboardMarkup;

public class Keyboard {

    public static final ReplyKeyboardMarkup KEYBOARD = new ReplyKeyboardMarkup(new KeyboardButton[][]{
            {new KeyboardButton("/echo hello world")}
    });

}
