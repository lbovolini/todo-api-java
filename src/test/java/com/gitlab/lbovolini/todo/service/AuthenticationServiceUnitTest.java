package com.gitlab.lbovolini.todo.service;

import com.gitlab.lbovolini.todo.model.User;
import com.gitlab.lbovolini.todo.repository.UserRepository;
import com.gitlab.lbovolini.todo.security.AuthenticatedUser;
import com.gitlab.lbovolini.todo.security.UserCredentials;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceUnitTest {

    private final String id = "5fecae92fc4878605c5c7a5b";
    private final String username = "lucas";
    private final String password = "pass11word";
    private final String hashedPassword = "$2a$12$oapo8oMaxkPBzCnD.GKieOL3kjVed/umWYrSWGzvzdvUshedUFCX2";
    private final int minimumTokenSize = 8; // "Bearer " + token

    private final User user = new User(id, username, hashedPassword);
    private final UserCredentials userCredentials = new UserCredentials(username, password);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void shouldAllowUserLoginWithCredentials() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        AuthenticatedUser authenticatedUser = authenticationService.login(userCredentials);

        assertEquals(id, authenticatedUser.getId());
        assertEquals(username, authenticatedUser.getUsername());
        assertTrue (minimumTokenSize <= authenticatedUser.getToken().length());
        assertNotNull(authenticatedUser.getExpiration());
    }

    @Test
    void shouldDenyUserLoginWithCredentialsWhenUserNotFound() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authenticationService.login(userCredentials);
        });

        assertEquals("Invalid username and/or password", exception.getMessage());
    }

}