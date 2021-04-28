package com.inflearn.demo.jpa;

import com.inflearn.demo.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jpa")
public class UserJpaController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        }

        EntityModel<User> model = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users"));

        return model;
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        }

        User storedUser = optUser.get();
        storedUser.setName(user.getName());
        storedUser.setPassword(user.getPassword());

        User updatedUser = userRepository.save(storedUser);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPostsByUser(@PathVariable Integer id) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        }

        return optUser.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable Integer id, @RequestBody Post post) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        }

        post.setUser(optUser.get());
        Post createdPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}/posts/{postId}")
    public void deletePost(@PathVariable Integer id, @PathVariable Integer postId) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty()) {
            throw new UserNotFoundException(String.format("ID[%s} not found", id));
        } else {
            postRepository.deleteById(postId);
        }
    }
}