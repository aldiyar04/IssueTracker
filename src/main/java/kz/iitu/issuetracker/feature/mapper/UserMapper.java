package kz.iitu.issuetracker.feature.mapper;

import kz.iitu.issuetracker.entity.User;
import kz.iitu.issuetracker.dto.user.request.UserSignupReq;
import kz.iitu.issuetracker.dto.user.response.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User entity);
    User signupReqToEntity(UserSignupReq signupReq);

    UserDto.Role toDtoRole(User.Role entityRole);
    User.Role toEntityRole(UserDto.Role dtoRole);
}
