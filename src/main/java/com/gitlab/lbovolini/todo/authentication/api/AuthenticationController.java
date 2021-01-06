package com.gitlab.lbovolini.todo.authentication.api;

import com.gitlab.lbovolini.todo.authentication.AuthenticatedUser;
import com.gitlab.lbovolini.todo.authentication.service.AuthenticationServiceImpl;
import com.gitlab.lbovolini.todo.authentication.UserCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticatedUser> login(@Valid @RequestBody UserCredentials userCredentials) {
        AuthenticatedUser authenticatedUser = authenticationService.login(userCredentials);

        return ResponseEntity.ok(authenticatedUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String username) {
        authenticationService.logout(username);

        return ResponseEntity.ok().build();
    }
}
