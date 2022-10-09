package kz.iitu.issuetracker.service;

import kz.iitu.issuetracker.dto.user.request.UserPasswdChangeReq;
import kz.iitu.issuetracker.dto.user.request.UserSignupReq;
import kz.iitu.issuetracker.dto.user.request.UserUpdateReq;
import kz.iitu.issuetracker.dto.user.response.UserDto;
import kz.iitu.issuetracker.dto.user.response.UserPaginatedResp;
import kz.iitu.issuetracker.entity.User;
import kz.iitu.issuetracker.feature.apiexception.ApiExceptionDetailHolder;
import kz.iitu.issuetracker.feature.apiexception.InvalidCredentialsException;
import kz.iitu.issuetracker.feature.apiexception.RecordAlreadyExistsException;
import kz.iitu.issuetracker.feature.mapper.UserMapper;
import kz.iitu.issuetracker.service.specification.UserRoleSpecification;
import kz.iitu.issuetracker.feature.apiexception.RecordNotFoundException;
import kz.iitu.issuetracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRoleSpecification roleSpec;
    private final PasswordEncoder passwordEncoder;

    public UserPaginatedResp getUsers(Pageable pageable,
                                      Optional<UserDto.Role> roleOptional,
                                      Optional<Boolean> isAssignedToProjectOptional) {
        Page<User> userPage = null;

        if (roleOptional.isEmpty() && isAssignedToProjectOptional.isEmpty()) {
            userPage = userRepository.findAll(pageable);
        } else if (roleOptional.isPresent()) {
            UserDto.Role role = roleOptional.get();
            if (isAssignedToProjectOptional.isEmpty()) {
                userPage = userRepository.findAll(roleSpec.getForUserDtoRole(role), pageable);
            } else {
                boolean isAssignedToProject = isAssignedToProjectOptional.get();
                userPage = findAssignedOrUnassignedDevs(role, isAssignedToProject, pageable);
            }
        }
        return UserPaginatedResp.fromUserPage(userPage);
    }

    private Page<User> findAssignedOrUnassignedDevs(UserDto.Role role, boolean isAssignedToProject,
                                                    Pageable pageable) {
        Page<User> userPage = null;
        if (role.equals(UserDto.Role.LEAD_DEV)) {
            if (isAssignedToProject) userPage = userRepository.findAssignedLeadDevs(pageable);
            else userPage = userRepository.findUnassignedLeadDevs(pageable);
        } else if (role.equals(UserDto.Role.DEVELOPER)) {
            if (isAssignedToProject) userPage = userRepository.findAssignedDevelopers(pageable);
            else userPage = userRepository.findUnassignedDevelopers(pageable);
        }
        return userPage;
    }

    public UserDto getById(long id) {
        User user = getByIdOrThrowNotFound(id);
        return toDto(user);
    }

    public UserDto register(UserSignupReq signupReq) {
        throwIfAlreadyTaken(signupReq.getEmail(), signupReq.getUsername());
        User user = toEntity(signupReq);
        user.setRole(User.Role.DEVELOPER);
        String encryptedPasswd = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPasswd);
        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    public UserDto update(long id, UserUpdateReq updateReq) {
        User user = getByIdOrThrowNotFound(id);

        User.Role newRole = toEntityRole(updateReq.getRole());
        String newEmail = updateReq.getEmail();
        String newUsername = updateReq.getUsername();

        throwIfAlreadyTaken(newEmail, newUsername);

        if (newRole != null) {
            user.setRole(newRole);
        }
        if (StringUtils.hasText(newEmail)) {
            user.setEmail(newEmail);
        }
        if (StringUtils.hasText(newUsername)) {
            user.setUsername(newUsername);
        }

        User updatedUser = userRepository.save(user);
        return toDto(updatedUser);
    }

    public void changePassword(long id, UserPasswdChangeReq passChangeReq) {
        User user = getByIdOrThrowNotFound(id);

        String suppliedRawPassword = passChangeReq.getOldPassword();
        String expectedPasswordHash = user.getPassword();
        throwIfPasswordNotMatches(suppliedRawPassword, expectedPasswordHash);

        String newPassword = passChangeReq.getNewPassword();
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public void delete(Long id) {
        User user = getByIdOrThrowNotFound(id);
        userRepository.delete(user);
    }

    private User toEntity(UserSignupReq signupReq) {
        return UserMapper.INSTANCE.signupReqToEntity(signupReq);
    }

    private UserDto toDto(User user) {
        return UserMapper.INSTANCE.toDto(user);
    }

    private User.Role toEntityRole(UserDto.Role dtoRole) {
        return UserMapper.INSTANCE.toEntityRole(dtoRole);
    }

    private void throwIfAlreadyTaken(String email, String username) {
        boolean isUsernameTaken = userRepository.existsByUsername(username);
        boolean isEmailTaken = userRepository.existsByEmail(email);

        List<ApiExceptionDetailHolder> exDetailHolders = new ArrayList<>();
        if (isEmailTaken) {
            exDetailHolders.add(ApiExceptionDetailHolder.builder()
                    .field(UserDto.Field.EMAIL)
                    .message("Email '" + email + "' is already taken")
                    .build());
        }
        if (isUsernameTaken) {
            exDetailHolders.add(ApiExceptionDetailHolder.builder()
                    .field(UserDto.Field.USERNAME)
                    .message("Username '" + username + "' is already taken")
                    .build());
        }
        if (!exDetailHolders.isEmpty()) {
            throw new RecordAlreadyExistsException(exDetailHolders);
        }
    }

    private User getByIdOrThrowNotFound(long id) {
        ApiExceptionDetailHolder exDetailHolder = ApiExceptionDetailHolder.builder()
                .field(UserDto.Field.ID)
                .message("User with id " + id + " does not exist")
                .build();
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(exDetailHolder));
    }

    private void throwIfPasswordNotMatches(String suppliedRawPassword, String expectedPasswordHash) {
        if (!passwordEncoder.matches(suppliedRawPassword, expectedPasswordHash)) {
            throw new InvalidCredentialsException();
        }
    }
}
