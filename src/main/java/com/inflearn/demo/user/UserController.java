package com.inflearn.demo.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public EntityModel<User> retrieveUser(@PathVariable Integer id) {
        User retrievedUser = userDaoService.findById(id);

        if (retrievedUser == null) {
            throw new UserNotFoundException(String.format("User ID[%s] not found", id));
        }

        // Resource<User> resource = new Resource<>(retrievedUser);
        EntityModel<User> model = EntityModel.of(retrievedUser);

        // ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(
        //         ControllerLinkBuilder.methodOn(this.getClass()).retrieveAllUsers()
        // );
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

        // resource.add(linkTo.withRel("all-users"));
        model.add(linkTo.withRel("all-users"));

        return model;
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
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