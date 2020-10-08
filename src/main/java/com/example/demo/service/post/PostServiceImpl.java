package com.example.demo.service.post;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.StatusPost;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Post model) {
        postRepository.save(model);
    }

    @Override
    public void remove(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Iterable<Post> findAllPostByAuthor(User user, StatusPost statusPost) {
        return postRepository.findAllByUserAndStatusPost(user, statusPost);
    }

    @Override
    public Iterable<Post> findAllPostByStatus(StatusPost statusPost) {
        return postRepository.findAllByStatusPost(statusPost);
    }
}
