package com.github.zjor.telegram.bot.framework.dispatch;

import com.github.zjor.telegram.bot.api.dto.Message;
import com.github.zjor.telegram.bot.api.dto.SendMessageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * Contains message handling result
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private List<SendMessageRequest> result;

    private Message message;

    public static Result empty(Message m) {
        return new Result(Collections.emptyList(), m);
    }

    public static Result completed(SendMessageRequest req, Message m) {
        return new Result(Collections.singletonList(req), m);
    }

    public static Result completed(List<SendMessageRequest> reqs, Message m) {
        return new Result(reqs, m);
    }

}
