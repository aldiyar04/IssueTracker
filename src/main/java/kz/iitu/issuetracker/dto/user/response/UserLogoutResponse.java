package kz.iitu.issuetracker.dto.user.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLogoutResponse {
    private final String status = "OK";
    private final boolean isLoggedOut = true;
}
