package com.gitlab.lbovolini.todo.authentication.service;

import com.gitlab.lbovolini.todo.authentication.AuthenticatedUser;
import com.gitlab.lbovolini.todo.authentication.UserCredentials;
import com.gitlab.lbovolini.todo.authentication.repository.AuthenticationRepository;
import com.gitlab.lbovolini.todo.common.DefaultUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplUnitTest {

    @Mock
    private AuthenticationRepository authenticationRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private final String id = "5fecae92fc4878605c5c7a5b";
    private final String username = "lucas";
    private final String password = "pass11word";
    private final String hashedPassword = "$2a$12$oapo8oMaxkPBzCnD.GKieOL3kjVed/umWYrSWGzvzdvUshedUFCX2";
    private final int minimumTokenSize = 8; // "Bearer " + token

    private DefaultUser defaultUser = new DefaultUser(id, username, hashedPassword);
    private final UserCredentials userCredentials = new UserCredentials(username, password);

    @Test
    void shouldAllowUserLoginWithCredentials() {
        when(authenticationRepository.findByUsername(username)).thenReturn(Optional.of(defaultUser));

        AuthenticatedUser authenticatedUser = authenticationService.login(userCredentials);

        assertEquals(id, authenticatedUser.getId());
        assertEquals(username, authenticatedUser.getUsername());
        assertTrue (minimumTokenSize <= authenticatedUser.getToken().length());
        assertNotNull(authenticatedUser.getExpiration());
    }

    @Test
    void shouldDenyUserLoginWithCredentialsWhenUserNotFound() {
        when(authenticationRepository.findByUsername(username)).thenReturn(Optional.empty());

        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () -> {
            authenticationService.login(userCredentials);
        });

        assertEquals("Invalid username and/or password", exception.getMessage());
    }

    @Test
    void shouldUnlockUserOnLogin() {
        when(authenticationRepository.findByUsername(username)).thenReturn(Optional.of(defaultUser));
        doNothing().when(authenticationRepository).unlockAccount(username);

        AuthenticatedUser authenticatedUser = authenticationService.login(userCredentials);

        verify(authenticationRepository, times(1)).unlockAccount(username);
    }

    @Test
    void shouldLogoutUser() {
        doNothing().when(authenticationRepository).lockAccount(username);
        authenticationService.logout(username);

        verify(authenticationRepository, times(1)).lockAccount(username);
    }
}