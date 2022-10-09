package kz.iitu.issuetracker.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DatabaseColumn.ID)
    Long id;

    @Version
    @Column(name = DatabaseColumn.VERSION)
    Long version = 1L;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Field {
        public static final String ID = "id";
        public static final String VERSION = "version";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DatabaseColumn {
        public static final String ID = "id";
        public static final String VERSION = "version";
    }
}
