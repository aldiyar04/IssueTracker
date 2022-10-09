package kz.iitu.issuetracker.dto.user.request;

import kz.iitu.issuetracker.dto.user.response.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UserUpdateReq {
    private final UserDto.Role role;
    private final String email;
    private final String username;
}
