package com.gitlab.lbovolini.todo.user.service;

import com.gitlab.lbovolini.todo.common.model.User;
import com.gitlab.lbovolini.todo.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserServiceImplUnitTest {

    private final String id = "5fecae92fc4878605c5c7a5b";
    private final String username = "lucas";
    private final String password = "pass11word";
    private final String hashedPassword = "$2a$12$oapo8oMaxkPBzCnD.GKieOL3kjVed/umWYrSWGzvzdvUshedUFCX2";

    private final User user = new User(id, username, password);
    private final User userWithHashedPassword = new User(id, username, hashedPassword);

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldDeleteUserById() {
        doNothing().when(userRepository).delete(id);
        userService.delete(id);

        verify(userRepository, times(1)).delete(id);
    }

    @Test
    void shouldFindUserById() {
        when(userRepository.findById(id)).thenReturn(Optional.of(userWithHashedPassword));
        Optional<User> userOptional = userService.findById(id);

        assertEquals(userWithHashedPassword, userOptional.get());
    }

    @Test
    void shouldFindUserByUsername() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userWithHashedPassword));
        Optional<User> userOptional = userService.findByUsername(username);

        assertEquals(userWithHashedPassword, userOptional.get());
    }

    @Test
    void shouldSaveUser() {
        when(userRepository.save(user)).thenReturn(userWithHashedPassword);
        User savedUser = userService.save(user);

        assertEquals(userWithHashedPassword, savedUser);
    }

    @Test
    void shouldConflictOnSaveUser() {
        when(userRepository.save(user)).thenThrow(DuplicateKeyException.class);

        assertThrows(DuplicateKeyException.class, () -> userService.save(user));
    }

    @Test
    void shouldUpdateUser() {
        when(userRepository.update(user)).thenReturn(Optional.of(userWithHashedPassword));
        Optional<User> updatedUserOptional = userService.update(user);

        assertEquals(userWithHashedPassword, updatedUserOptional.get());
    }

    @Test
    void shouldNotFoundUserToUpdate() {
        when(userRepository.update(user)).thenReturn(Optional.empty());
        Optional<User> updatedUserOptional = userService.update(user);

        assertEquals(Optional.empty(), updatedUserOptional);
    }

    @Test
    void shouldConflictOnUpdateUser() {
        when(userRepository.update(user)).thenThrow(DuplicateKeyException.class);

        assertThrows(DuplicateKeyException.class, () -> userService.update(user));
    }
}