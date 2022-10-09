package kz.iitu.issuetracker.dto.user.response;

import lombok.Data;

@Data
public class UserLoginResp {
    private final String jwtToken;
    private final UserDto user;
}
