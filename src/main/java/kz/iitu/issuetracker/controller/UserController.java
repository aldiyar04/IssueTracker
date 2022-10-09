package kz.iitu.issuetracker.controller;

import kz.iitu.issuetracker.controller.compoundrequestparam.UserFilterReq;
import kz.iitu.issuetracker.controller.compoundrequestparam.annotation.CompoundRequestParam;
import kz.iitu.issuetracker.dto.user.request.UserPasswdChangeReq;
import kz.iitu.issuetracker.dto.user.request.UserSignupReq;
import kz.iitu.issuetracker.dto.user.request.UserUpdateReq;
import kz.iitu.issuetracker.dto.user.response.UserDto;
import kz.iitu.issuetracker.dto.user.response.UserPaginatedResp;
import kz.iitu.issuetracker.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('Admin')")
@ResponseStatus(HttpStatus.OK)
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserPaginatedResp getUsers(Pageable pageable, @Valid @CompoundRequestParam UserFilterReq filterReq) {
        return userService.getUsers(pageable,
                Optional.ofNullable(filterReq.getRole()),
                Optional.ofNullable(filterReq.getIsAssignedToProject()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Admin') || @userSecurity.hasUserId(authentication, #id)")
    public UserDto getUserById(@PathVariable("id") long id) {
        return userService.getById(id);
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@Valid @RequestBody UserSignupReq signupReq) {
        return userService.register(signupReq);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin') || @userSecurity.hasUserId(authentication, #id)")
    public UserDto updateUser(@PathVariable("id") long id,
                              @RequestBody UserUpdateReq updateReq) {
        return userService.update(id, updateReq);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@userSecurity.hasUserId(authentication, #id)")
    public void changePassword(@PathVariable("id") long id,
                               @RequestBody UserPasswdChangeReq passwdChangeReq) {
        userService.changePassword(id, passwdChangeReq);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin') || @userSecurity.hasUserId(authentication, #id)")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
    }
}
