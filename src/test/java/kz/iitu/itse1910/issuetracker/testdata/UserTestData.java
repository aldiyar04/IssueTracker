package kz.iitu.itse1910.issuetracker.testdata;

import kz.iitu.issuetracker.dto.user.response.UserDto;
import kz.iitu.issuetracker.entity.User;
import kz.iitu.issuetracker.feature.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class UserTestData {
    @Getter
    public static class Entity {
        private final User admin = User.builder()
                .id(1L)
                .role(User.Role.ADMIN)
                .email("admin@test.com")
                .username("AdminUsername")
                .build();
        private final User manager = User.builder()
                .id(2L)
                .role(User.Role.MANAGER)
                .email("manager@test.com")
                .username("ManagerUsername")
                .build();
        private final User leadDev1 = User.builder()
                .id(3L)
                .role(User.Role.LEAD_DEV)
                .email("leaddev1@test.com")
                .username("LeadDevOne")
                .build();
        private final User leadDev2 = User.builder()
                .id(4L)
                .role(User.Role.LEAD_DEV)
                .email("leaddev2@test.com")
                .username("LeadDevTwo")
                .build();
        private final User developer1 = User.builder()
                .id(5L)
                .role(User.Role.DEVELOPER)
                .email("developer1@test.com")
                .username("DeveloperOne")
                .build();
        private final User developer2 = User.builder()
                .id(6L)
                .role(User.Role.DEVELOPER)
                .email("developer2@test.com")
                .username("DeveloperTwo")
                .build();

        public List<User> getAllUsers() {
            return List.of(admin, manager, leadDev1, leadDev2, developer1, developer2);
        }

        public List<User> getAllAdmins() {
            return List.of(admin);
        }

        public List<User> getAllManagers() {
            return List.of(manager);
        }

        public List<User> getAllLeadDevs() {
            return List.of(leadDev1, leadDev2);
        }

        public List<User> getAllDevelopers() {
            return List.of(developer1, developer2);
        }

        public List<User> getUnassignedLeadDevs() {
            return List.of(leadDev1);
        }

        public List<User> getAssignedLeadDevs() {
            return List.of(leadDev2);
        }

        public List<User> getUnassignedDevelopers() {
            return List.of(developer1);
        }

        public List<User> getAssignedDevelopers() {
            return List.of(developer2);
        }
    }

    @Getter
    public static class Dto {
        @Getter(AccessLevel.NONE)
        private final Entity entities = new Entity();

        private final UserDto admin = toDto(entities.getAdmin());
        private final UserDto manager = toDto(entities.getManager());
        private final UserDto leadDev1 = toDto(entities.getLeadDev1());
        private final UserDto leadDev2 = toDto(entities.getLeadDev2());
        private final UserDto developer1 = toDto(entities.getDeveloper1());
        private final UserDto developer2 = toDto(entities.getDeveloper2());

        private UserDto toDto(User user) {
            return UserMapper.INSTANCE.toDto(user);
        }

        public List<UserDto> getAllUsers() {
            return List.of(admin, manager, leadDev1, leadDev2, developer1, developer2);
        }

        public List<UserDto> getAllAdmins() {
            return List.of(admin);
        }

        public List<UserDto> getAllManagers() {
            return List.of(manager);
        }

        public List<UserDto> getAllLeadDevs() {
            return List.of(leadDev1, leadDev2);
        }

        public List<UserDto> getAllDevelopers() {
            return List.of(developer1, developer2);
        }

        public List<UserDto> getUnassignedLeadDevs() {
            return List.of(leadDev1);
        }

        public List<UserDto> getAssignedLeadDevs() {
            return List.of(leadDev2);
        }

        public List<UserDto> getUnassignedDevelopers() {
            return List.of(developer1);
        }

        public List<UserDto> getAssignedDevelopers() {
            return List.of(developer2);
        }
    }

    public static class RoleSpecification {
        public Specification<User> getForAdmin() {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User.Field.ROLE),
                    User.Role.ADMIN);
        }

        public Specification<User> getForManager() {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User.Field.ROLE),
                    User.Role.MANAGER);
        }

        public Specification<User> getForLeadDev() {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User.Field.ROLE),
                    User.Role.LEAD_DEV);
        }

        public Specification<User> getForDeveloper() {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User.Field.ROLE),
                    User.Role.DEVELOPER);
        }
    }
}
