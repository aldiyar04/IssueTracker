package kz.iitu.issuetracker.dto.project.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class ProjectDto {
    private final Long id;
    private final String name;
    private final String description;
    private final Long leadDevId;
    private final String leadDevUsername;
    private final String createdOn;
    private final String updatedOn;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Field {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String LEAD_DEV_ID = "leadDevId";
        public static final String LEAD_DEV_USERNAME = "leadDevUsername";
        public static final String CREATED_ON = "createdOn";
        public static final String UPDATED_ON = "updatedOn";
    }
}
