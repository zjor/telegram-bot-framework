package com.github.zjor.telegram.bot.framework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Comparator;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(of = "id")
public class Model {

    public final static Comparator<Model> ID_COMPARATOR = Comparator.comparing(Model::getId);

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", unique = true, length = 36)
    private String id;

    /**
     * Null-safe method of printing object id's. Primarily intended for use in toString methods.
     *
     * @param entity entity to get id from
     * @return entity id, or null if entity is null;
     */
    public static String id(Model entity) {
        return (entity == null) ? null : entity.id;
    }


}
