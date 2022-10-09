package kz.iitu.issuetracker.service;

import kz.iitu.issuetracker.dto.user.request.UserLoginReq;
import kz.iitu.issuetracker.dto.user.response.UserLoginResp;
import kz.iitu.issuetracker.entity.User;
import kz.iitu.issuetracker.feature.apiexception.InvalidCredentialsException;
import kz.iitu.issuetracker.feature.apiexception.UnauthorizedException;
import kz.iitu.issuetracker.feature.mapper.UserMapper;
import kz.iitu.issuetracker.feature.security.userdetails.UserDetailsImpl;
import kz.iitu.issuetracker.entity.AuthToken;
import kz.iitu.issuetracker.feature.security.jwtauth.JwtUtil;
import kz.iitu.issuetracker.repository.AuthTokenRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final AuthTokenRepository authTokenRepository;

    public UserLoginResp authenticateUser(UserLoginReq loginReq) {
        Authentication auth;
        try {
            auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }

        User user = getUser(auth);
        String jwtToken = jwtUtil.generateToken(user);
        // remove all previous login session tokens
        authTokenRepository.deleteAllByUsername(user.getUsername());
        // initiate login session, on logout this token will be deleted from DB and become invalid
        authTokenRepository.save(new AuthToken(jwtToken, user.getUsername()));

        return new UserLoginResp(
                jwtToken,
                UserMapper.INSTANCE.toDto(user)
        );
    }

    public void logoutUser(String authorizationHeader) {
        if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("No authentication provided");
        }
        String jwtToken = authorizationHeader.split(" ")[1].trim();

        Optional<AuthToken> authTokenOptional = authTokenRepository.findByToken(jwtToken);
        if (authTokenOptional.isEmpty()) {
            return;
        }
        AuthToken authToken = authTokenOptional.get();
        authTokenRepository.delete(authToken);
    }

    private User getUser(Authentication auth) {
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getUser();
    }
}
