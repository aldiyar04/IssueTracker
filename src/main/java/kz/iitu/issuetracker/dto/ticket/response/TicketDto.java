package kz.iitu.issuetracker.dto.ticket.response;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class TicketDto {
    private final Long id;
    private final String title;
    private final String description;
    private final Long projectId;
    private final String projectName;
    private final Long assigneeId;
    private final String assigneeUsername;
    private final Long submitterId;
    private final String submitterUsername;
    private final Type type;
    private final Status status;
    private final Priority priority;
    private final LocalDate targetResolutionDate;
    private final LocalDate actualResolutionDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Field {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String PROJECT_ID = "projectId";
        public static final String PROJECT_NAME = "projectName";
        public static final String ASSIGNEE_ID = "assigneeId";
        public static final String ASSIGNEE_USERNAME = "assigneeUsername";
        public static final String SUBMITTER_ID = "submitterId";
        public static final String SUBMITTER_USERNAME = "submitterUsername";
        public static final String TYPE = "type";
        public static final String STATUS = "status";
        public static final String PRIORITY = "priority";
        public static final String TARGET_RES_DATE = "targetResolutionDate";
        public static final String ACTUAL_RES_DATE = "actualResolutionDate";
        public static final String CREATED_AT = "createdAt";
        public static final String UPDATED_AT = "updatedAt";
    }

    public enum Type {
        BUG("Bug"), VULNERABILITY("Vulnerability"), FEATURE_REQUEST("Feature Request"),
        REFACTORING("Refactoring"), OTHER("Other");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

    public enum Status {
        NEW("New"), ASSIGNED("Assigned"), IN_PROGRESS("In Progress"), SUBMITTED("Submitted"),
        EXTRA_WORK_REQUIRED("Extra Work Required"), RESOLVED("Resolved");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }

    public enum Priority {
        CRITICAL("Critical"), HIGH("High"), MEDIUM("Medium"), LOW("Low"), NONE("None");

        private final String value;

        Priority(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
