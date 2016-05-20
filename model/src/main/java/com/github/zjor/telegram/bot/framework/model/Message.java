package com.github.zjor.telegram.bot.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "message")
public class Message extends Born {

    /**
     * Telegram message identifier. Not unique among users.
     */
    @Column(name = "message_id")
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private TelegramUser owner;

    /**
     * JSON-content of received message
     */
    @Column(name = "message", length = 8192, nullable = true)
    private String message;

}
