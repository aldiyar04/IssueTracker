package kz.iitu.issuetracker.controller;

import kz.iitu.issuetracker.dto.user.request.UserLoginReq;
import kz.iitu.issuetracker.dto.user.response.UserLoginResp;
import kz.iitu.issuetracker.dto.user.response.UserLogoutResponse;
import kz.iitu.issuetracker.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

@RestController
@ResponseStatus(HttpStatus.OK)
@DenyAll
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    @PermitAll
    public ResponseEntity<UserLoginResp> authenticate(@Valid @RequestBody UserLoginReq loginReq) {
        UserLoginResp jwtUserResp = authService.authenticateUser(loginReq);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtUserResp.getJwtToken())
                .body(jwtUserResp);
    }

    @DeleteMapping("/signout")
    @PermitAll
    public UserLogoutResponse logoutUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        authService.logoutUser(authorization);
        return new UserLogoutResponse();
    }
}
