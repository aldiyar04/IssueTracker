package kz.iitu.issuetracker.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserPasswdChangeReq {
    private final String oldPassword;
    private final String newPassword;
}
