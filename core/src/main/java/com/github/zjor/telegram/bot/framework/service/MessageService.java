package com.github.zjor.telegram.bot.framework.service;

import com.github.jtail.jpa.util.EntityUtils;
import com.github.zjor.telegram.bot.framework.model.Born_;
import com.github.zjor.telegram.bot.framework.model.Message;
import com.github.zjor.telegram.bot.framework.model.Message_;
import com.github.zjor.telegram.bot.framework.model.TelegramUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class MessageService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserService userService;

    //TODO: refactor objects to DTO
    @Transactional
    public Message create(com.github.zjor.telegram.bot.api.dto.Message message) {
        return create(message, userService.ensureExists(message.getFrom()));
    }

    @Transactional
    public Message create(com.github.zjor.telegram.bot.api.dto.Message message, TelegramUser user) {
        Message entity = new Message(message.getMessageId(), user, message.getText());
        entity.setBorn(new Date()); //TODO: use received time
        return EntityUtils.persist(em, entity);
    }


    /**
     * Returns last n messages of given user sorted by creation date desc.
     * @param user
     * @param n
     * @return
     */
    public List<Message> last(TelegramUser user, int n) {
        return EntityUtils.find(em, Message.class).has(Message_.owner, user).desc(Born_.born).list(n);
    }


}
