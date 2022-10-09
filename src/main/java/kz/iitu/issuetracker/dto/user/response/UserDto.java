package kz.iitu.issuetracker.dto.user.response;


import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class UserDto {
    private final Long id;
    private final Role role;
    private final String email;
    private final String username;
    private final LocalDate createdOn;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Field {
        public static final String ID = "id";
        public static final String ROLE = "role";
        public static final String EMAIL = "email";
        public static final String USERNAME = "username";
        public static final String CREATED_ON = "createdOn";
    }

    public enum Role {
        ADMIN("Admin"), MANAGER("Manager"), LEAD_DEV("Lead Dev"), DEVELOPER("Developer");

        private final String value;

        Role(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
