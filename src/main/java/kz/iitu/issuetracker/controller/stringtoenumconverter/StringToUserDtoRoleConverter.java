package kz.iitu.issuetracker.controller.stringtoenumconverter;

import kz.iitu.issuetracker.controller.api.UserApi;
import kz.iitu.issuetracker.dto.user.response.UserDto;
import kz.iitu.issuetracker.feature.apiexception.ApiException;
import kz.iitu.issuetracker.feature.apiexception.ApiExceptionDetailHolder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUserDtoRoleConverter implements Converter<String, UserDto.Role> {
    @Override
    public UserDto.Role convert(String source) {
        switch (source) {
            case UserApi.Role.DEVELOPER:
                return UserDto.Role.DEVELOPER;
            case UserApi.Role.LEAD_DEV:
                return UserDto.Role.LEAD_DEV;
            case UserApi.Role.MANAGER:
                return UserDto.Role.MANAGER;
            case UserApi.Role.ADMIN:
                return UserDto.Role.ADMIN;
            default:
                String exMsg = String.format("Role '%s' doesn't exist. Valid roles are: '%s', '%s', '%s', '%s'.", source,
                        UserApi.Role.DEVELOPER, UserApi.Role.LEAD_DEV, UserApi.Role.MANAGER, UserApi.Role.ADMIN);
                ApiExceptionDetailHolder exDetailHolder = ApiExceptionDetailHolder.builder()
                        .message(exMsg)
                        .build();
                throw new ApiException(exDetailHolder);
        }
    }
}
