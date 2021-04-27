package com.inflearn.demo.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private final UserDaoService userDaoService;

    public UserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public User retrieveUser(@PathVariable Integer id) {
        User retrievedUser = userDaoService.findById(id);

        if (retrievedUser == null) {
            throw new UserNotFoundException(String.format("User ID[%s] not found", id));
        }

        return retrievedUser;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userDaoService.save(user);

        // CurrentRequest(localhost:8000/users) + Path(/{id}) = URI(localhost:8000/users/{id})
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        // URL + 201(created)
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        User deletedUser = userDaoService.deleteById(id);

        if (deletedUser == null) {
            throw new UserNotFoundException(String.format("User ID[%s] not found", id));
        }
    }
}