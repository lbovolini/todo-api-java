package com.gitlab.lbovolini.todo.controller;

import com.gitlab.lbovolini.todo.model.User;
import com.gitlab.lbovolini.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Consome: MediaType.APPLICATION_JSON
 * Produz: MediaType.APPLICATION_JSON
 */
@RestController
@RequestMapping(path = "api/v1/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements CrudController<User> {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Remove o usuário com o id informado.
     * @param id
     * @return Retorna ResponseEntity com HttpStatus 204.
     */
    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userService.delete(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Realiza a busca do usuário pelo id informado.
     * @param id
     * @return Retorna ResponseEntity com o usuário e com HttpStatus 200, ou HttpStatus 404 se o usuário com o id informado não for encontrado.
     */
    @Override
    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable String id) {

        Optional<User> userOptional = userService.findById(id);

        return userOptional.stream()
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Realiza a busca de todos os usuários.
     * @return Retorna ResponseEntity com a lista de todos os usuários e com HttpStatus 200.
     */
    @Override
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> userList = userService.findAll();

        return ResponseEntity.ok(userList);
    }

    /**
     * Salva o usuário na base de dados.
     * @param user
     * @return Retorna ResponseEntity com o usuário salvo e com HttpStatus 200, ou HttpStatus 409 se o usuário informado já existe na base de dados.
     */
    @Override
    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);

        return ResponseEntity.ok(savedUser);
    }

    /**
     * Atualiza o usuário na base de dados.
     * @param user
     * @return Retorna ResponseEntity com o usuário atualizado e com HttpStatus 200, ou HttpStatus 404 se o usuário com o id informado não for encontrado.
     */
    @Override
    @PutMapping
    public ResponseEntity<User> update(@Valid @RequestBody User user) {
        Optional<User> userOptional = userService.update(user);

        return userOptional.stream()
                .map(ResponseEntity::ok)
                .findFirst()
                .orElse(ResponseEntity.notFound().build());
    }
}
