package kz.iitu.issuetracker.service.specification;

import kz.iitu.issuetracker.dto.user.response.UserDto;
import kz.iitu.issuetracker.entity.User;
import kz.iitu.issuetracker.feature.mapper.UserMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserRoleSpecification {
    public Specification<User> getForUserDtoRole(UserDto.Role userDtoRole) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(User.Field.ROLE),
                toUserEntityRole(userDtoRole));
    }

    private User.Role toUserEntityRole(UserDto.Role userDtoRole) {
        return UserMapper.INSTANCE.toEntityRole(userDtoRole);
    }
}
