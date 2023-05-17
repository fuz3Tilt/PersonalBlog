package ru.kradin.blog.models;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class SuperEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (! (o instanceof SuperEntity)) return false;
        SuperEntity that = (SuperEntity) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
