package com.github.zjor.telegram.bot.framework.service;

import com.github.jtail.jpa.util.EntityUtils;
import com.github.zjor.telegram.bot.api.dto.User;
import com.github.zjor.telegram.bot.framework.model.TelegramUser;
import com.github.zjor.telegram.bot.framework.model.TelegramUser_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public TelegramUser ensureExists(User user) {
        return getOpt(user.getId()).orElseGet(() -> create(user, new Date()));
    }

    public TelegramUser create(User user, Date born) {
        TelegramUser entity = new TelegramUser(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername());
        entity.setBorn(born);
        return EntityUtils.persist(em, entity);
    }

    public Optional<TelegramUser> getOpt(int telegramId) {
        return EntityUtils.find(em, TelegramUser.class).has(TelegramUser_.telegramId, telegramId).first();
    }

    public long count() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> q = cb.createQuery(Long.class);
        q.select(cb.count(q.from(User.class)));
        return em.createQuery(q).getSingleResult();
    }

}
