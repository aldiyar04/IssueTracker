package kz.iitu.issuetracker.controller.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserApi {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Filter {
        public static final String ROLE = "role";
        public static final String IS_ASSIGNED_TO_PROJECT = "is-assigned-to-project";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Role {
        public static final String DEVELOPER = "developer";
        public static final String LEAD_DEV = "lead-dev";
        public static final String MANAGER = "manager";
        public static final String ADMIN = "admin";
    }
}
