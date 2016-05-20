package com.github.zjor.telegram.bot.framework.demo.echo.handlers;

import com.github.zjor.telegram.bot.api.Telegram;
import com.github.zjor.telegram.bot.api.dto.AnswerInlineQuery;
import com.github.zjor.telegram.bot.api.dto.InlineQuery;
import com.github.zjor.telegram.bot.api.dto.InlineQueryResultArticle;
import com.github.zjor.telegram.bot.api.dto.InputTextMessageContent;
import com.github.zjor.telegram.bot.framework.service.MessageService;
import com.github.zjor.telegram.bot.framework.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Returns previously entered messages
 */
@Slf4j
public class InlineQueryHandler implements Consumer<InlineQuery> {

    @Inject
    private Telegram telegram;

    @Inject
    private UserService userService;

    @Inject
    private MessageService messageService;

    @Override
    public void accept(InlineQuery inlineQuery) {
        List<InlineQueryResultArticle> as =
                messageService.last(userService.getOpt(inlineQuery.getFrom().getId()).get(), 50).stream()
                        .map(messageService::toDto)
                        .map(m -> {
                            InlineQueryResultArticle article = new InlineQueryResultArticle();
                            article.setId(String.valueOf(m.getMessageId()));
                            article.setTitle(m.getText());
                            article.setInputMessageContent(new InputTextMessageContent(m.getText(), null, null));
                            return article;
                        }).collect(Collectors.toList());

        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery(inlineQuery.getId(), as.toArray(new InlineQueryResultArticle[]{}));
        telegram.answerInlineQuery(answerInlineQuery);
    }
}
