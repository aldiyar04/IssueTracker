package integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.iitu.issuetracker.IssueTrackerApplication;
import kz.iitu.issuetracker.controller.UserController;
import kz.iitu.issuetracker.controller.aop.ExceptionHandlerDelegate;
import kz.iitu.issuetracker.controller.api.UserApi;
import kz.iitu.issuetracker.controller.compoundrequestparam.argumentresolver.CompoundRequestParamArgumentResolver;
import kz.iitu.issuetracker.controller.aop.CustomExceptionHandler;
import kz.iitu.issuetracker.dto.user.response.UserPaginatedResp;
import kz.iitu.issuetracker.entity.User;
import kz.iitu.issuetracker.service.UserService;
import kz.iitu.itse1910.issuetracker.testdata.UserTestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = {IssueTrackerApplication.class})
class UserControllerTest {
    @MockBean
    UserService userService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CompoundRequestParamArgumentResolver compoundRequestParamArgumentResolver;
    @Autowired
    UserController underTest;

    MockMvc mockMvc;

    @BeforeEach
    void initMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(underTest)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(),
                        compoundRequestParamArgumentResolver)
                .setControllerAdvice(new CustomExceptionHandler(new ExceptionHandlerDelegate()))
                .build();
    }

    UserTestData.Entity users = new UserTestData.Entity();

    @Test
    void getUsers_shouldReturnOk_whenRoleAndIsAssignedNull() throws Exception {
        UserPaginatedResp expectedResp = userPaginatedResp(users.getAllUsers());
        when(userService.getUsers(any(), any(), any()))
                .thenReturn(expectedResp);

        // when, then
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(expectedResp)));
    }

    private UserPaginatedResp userPaginatedResp(List<User> userList) {
        return UserPaginatedResp.fromUserPage(new PageImpl<>(userList));
    }

    private String toJson(Object responseObject) throws JsonProcessingException {
        return objectMapper.writeValueAsString(responseObject);
    }

    @Test
    void getUsers_shouldThrowMethodArgumentNotValid_whenRoleNotLeadDevOrDeveloperAndIsAssignedNotNull() throws Exception {
        String isAssignedToProject = "true";

        String nullRole = null;
        String adminRole = UserApi.Role.ADMIN;
        String managerRole = UserApi.Role.MANAGER;

        getUsers_performInvalidRequest(nullRole, isAssignedToProject);
        getUsers_performInvalidRequest(adminRole, isAssignedToProject);
        getUsers_performInvalidRequest(managerRole, isAssignedToProject);
    }

    private void getUsers_performInvalidRequest(String role, String isAssignedToProject) throws Exception {
        mockMvc.perform(get("/users")
                        .param(UserApi.Filter.ROLE, role)
                        .param(UserApi.Filter.IS_ASSIGNED_TO_PROJECT, isAssignedToProject))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }
}
