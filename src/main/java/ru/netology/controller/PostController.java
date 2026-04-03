package ru.netology.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.netology.exception.PostNotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable long id) {
        try {
            Post post = service.getById(id);
            return ResponseEntity.ok(post);
        } catch (PostNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Post save(@RequestBody Post post) {
        return service.save(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeById(@PathVariable long id) {
        try {
            service.removeById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (PostNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public Map<String, String> handlePostNotFound(PostNotFoundException e) {
        return Map.of("error", e.getMessage());
    }


}