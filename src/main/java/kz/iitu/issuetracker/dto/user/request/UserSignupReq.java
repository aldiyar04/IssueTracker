package kz.iitu.issuetracker.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@ToString
public class UserSignupReq {
    @NotNull
    private final String email;
    @NotNull
    private final String username;
    @NotNull
    private final String password;
}
