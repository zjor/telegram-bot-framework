package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.framework.model.Message;
import com.github.zjor.telegram.bot.framework.model.TelegramUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageContext {

    private TelegramUser user;

    private List<Message> conversation;

    private Message currentMessage;

}
