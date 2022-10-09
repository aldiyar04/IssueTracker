package kz.iitu.issuetracker.dto.user.request;

import lombok.Data;

@Data
public class UserLoginReq {
    private final String username;
    private final String password;
}
