package kz.iitu.issuetracker.controller.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketApi {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Filter {
        public static final String TYPE = "type";
        public static final String STATUS = "status";
        public static final String PRIORITY = "priority";
        public static final String IS_ASSIGNED = "is-assigned";
        public static final String IS_OVERDUE = "is-overdue";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Type {
        public static final String BUG = "bug";
        public static final String VULNERABILITY = "vulnerability";
        public static final String FEATURE_REQUEST = "feature-request";
        public static final String REFACTORING = "refactoring";
        public static final String OTHER = "other";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Status {
        public static final String NEW = "new";
        public static final String ASSIGNED = "assigned";
        public static final String IN_PROGRESS = "in-progress";
        public static final String SUBMITTED = "submitted";
        public static final String EXTRA_WORK_REQUIRED = "extra-work-required";
        public static final String RESOLVED = "resolved";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Priority {
        public static final String CRITICAL = "critical";
        public static final String HIGH = "high";
        public static final String MEDIUM = "medium";
        public static final String LOW = "low";
        public static final String NONE = "none";
    }
}
