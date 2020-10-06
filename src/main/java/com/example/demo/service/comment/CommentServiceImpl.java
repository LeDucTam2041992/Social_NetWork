package com.example.demo.service.comment;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Override
    public Iterable<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Comment model) {
        commentRepository.save(model);
    }

    @Override
    public void remove(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Iterable<Comment> findAllByPost(Post post) {
        return commentRepository.findAllByPost(post);
    }
}
