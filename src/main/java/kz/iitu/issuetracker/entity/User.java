package kz.iitu.issuetracker.entity;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class User extends BaseEntity {
    @Column(name = DatabaseColumn.ROLE)
    private Role role;

    @Column(name = DatabaseColumn.EMAIL)
    private String email;

    @Column(name = DatabaseColumn.USERNAME)
    private String username;

    @Column(name = DatabaseColumn.PASSWORD)
    private String password;

    @Column(name = DatabaseColumn.CREATED_ON)
    private LocalDate createdOn;

    @PrePersist
    private void setCreatedOn() {
        createdOn = LocalDate.now();
    }

    @Builder
    public User(Long id, Long version, Role role, String email, String username, String password, LocalDate createdOn) {
        super(id, version);
        this.role = role;
        this.email = email;
        this.username = username;
        this.password = password;
        this.createdOn = createdOn;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Field {
        public static final String ROLE = "role";
        public static final String EMAIL = "email";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String CREATED_ON = "createdOn";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DatabaseColumn {
        public static final String ROLE = "role";
        public static final String EMAIL = "email";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String CREATED_ON = "created_on";
    }

    public enum Role {
        ADMIN("Admin"), MANAGER("Manager"), LEAD_DEV("Lead Dev"), DEVELOPER("Developer");

        private final String value;

        Role(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
