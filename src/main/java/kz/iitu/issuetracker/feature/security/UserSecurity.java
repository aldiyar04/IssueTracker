package kz.iitu.issuetracker.feature.security;

import kz.iitu.issuetracker.entity.Project;
import kz.iitu.issuetracker.entity.User;
import kz.iitu.issuetracker.feature.apiexception.RecordNotFoundException;
import kz.iitu.issuetracker.feature.security.userdetails.UserDetailsImpl;
import kz.iitu.issuetracker.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UserSecurity {
    private final ProjectRepository projectRepository;

    public boolean hasUserId(Authentication authentication, Long userId) {
        if (isAnonymousUser(authentication)) {
            return false;
        }
        User user = getUser(authentication);
        return Objects.equals(user.getId(), userId);
    }

    public boolean isLoggedIn(Authentication authentication) {
        return !isAnonymousUser(authentication);
    }

    @Transactional
    public boolean isLeadDevOfProject(Authentication authentication, Long projectId) {
        if (isAnonymousUser(authentication)) {
            return false;
        }
        User user = getUser(authentication);
        if (!Objects.equals(user.getRole(), User.Role.LEAD_DEV)) {
            return false;
        }
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RecordNotFoundException("Project with id " + projectId + " does not exist"));
        return project.getLeadDev() != null && Objects.equals(project.getLeadDev().getId(), user.getId());
    }

    private boolean isAnonymousUser(Authentication authentication) {
        return authentication instanceof AnonymousAuthenticationToken;
    }

    private User getUser(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
