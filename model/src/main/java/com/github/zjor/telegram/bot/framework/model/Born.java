package com.github.zjor.telegram.bot.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class Born extends Model {

    /**
     * The moment of time when this object was created. This is expected to reflect
     * when object was written into DB and not some custom date.
     */
    @Column(name = "born", nullable = false)
    private Date born;

}
