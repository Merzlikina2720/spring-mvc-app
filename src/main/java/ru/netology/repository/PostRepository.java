package ru.netology.repository;

import ru.netology.exception.PostNotFoundException;
import ru.netology.model.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@org.springframework.stereotype.Repository
public class PostRepository {
    public final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Post getById(long id) {
        Post post = posts.get(id);
        if (post == null) {
            throw new PostNotFoundException("Post with id " + id + " not found");
        }
        return post;
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(counter.getAndIncrement());
        }
        posts.put(post.getId(), post);
        return post;
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}