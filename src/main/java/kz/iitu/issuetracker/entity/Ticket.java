package kz.iitu.issuetracker.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Ticket extends BaseEntity {
    @Column(name = DatabaseColumn.TITLE)
    private String title;

    @Column(name = DatabaseColumn.DESCRIPTION)
    private String description;

    @ManyToOne
    @JoinColumn(name = DatabaseColumn.PROJECT_ID)
    private Project project;

    @ManyToOne
    @JoinColumn(name = DatabaseColumn.ASSIGNEE_ID)
    private User assignee;

    @ManyToOne
    @JoinColumn(name = DatabaseColumn.SUBMITTER_ID)
    private User submitter;

    @Column(name = DatabaseColumn.TYPE)
    private Type type;

    @Column(name = DatabaseColumn.STATUS)
    private Status status;

    @Column(name = DatabaseColumn.PRIORITY)
    private Priority priority;

    @Column(name = DatabaseColumn.TARGET_RESOLUTION_DATE)
    private LocalDate targetResolutionDate;

    @Column(name = DatabaseColumn.ACTUAL_RESOLUTION_DATE)
    private LocalDate actualResolutionDate;

    @Column(name = DatabaseColumn.CREATED_AT)
    private LocalDateTime createdAt;

    @Column(name = DatabaseColumn.UPDATED_AT)
    private LocalDateTime updatedAt;

    @PrePersist
    private void setCreatedAtAndUpdatedAt() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void setUpdatedAt() {
        updatedAt = LocalDateTime.now();
    }

    @Builder
    public Ticket(Long id, Long version, String title, String description, Project project,
                  User assignee, User submitter, Type type, Status status, Priority priority,
                  LocalDate targetResolutionDate, LocalDate actualResolutionDate,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, version);
        this.title = title;
        this.description = description;
        this.project = project;
        this.assignee = assignee;
        this.submitter = submitter;
        this.type = type;
        this.status = status;
        this.priority = priority;
        this.targetResolutionDate = targetResolutionDate;
        this.actualResolutionDate = actualResolutionDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", version=" + version +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", projectId=" + project.getId() +
                ", assigneeId=" + (assignee == null ? "null" : "" + assignee.getId()) +
                ", submitterId=" + (submitter == null ? "=null" : "" + submitter.getId()) +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", targetResolutionDate=" + targetResolutionDate +
                ", actualResolutionDate=" + actualResolutionDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Field {
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String PROJECT = "project";
        public static final String ASSIGNEE = "assignee";
        public static final String SUBMITTER = "submitter";
        public static final String TYPE = "type";
        public static final String STATUS = "status";
        public static final String PRIORITY = "priority";
        public static final String TARGET_RESOLUTION_DATE = "targetResolutionDate";
        public static final String ACTUAL_RESOLUTION_DATE = "actualResolutionDate";
        public static final String CREATED_AT = "createdAt";
        public static final String UPDATED_AT = "updatedAt";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DatabaseColumn {
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String PROJECT_ID = "project_id";
        public static final String ASSIGNEE_ID = "assignee_id";
        public static final String SUBMITTER_ID = "submitter_id";
        public static final String TYPE = "type";
        public static final String STATUS = "status";
        public static final String PRIORITY = "priority";
        public static final String TARGET_RESOLUTION_DATE = "target_res_date";
        public static final String ACTUAL_RESOLUTION_DATE = "actual_res_date";
        public static final String CREATED_AT = "created_at";
        public static final String UPDATED_AT = "updated_at";
    }

    public enum Type {
        BUG("Bug"), VULNERABILITY("Vulnerability"), FEATURE_REQUEST("Feature Request"),
        REFACTORING("Refactoring"), OTHER("Other");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        @Override
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
        public String toString() {
            return value;
        }
    }
}
