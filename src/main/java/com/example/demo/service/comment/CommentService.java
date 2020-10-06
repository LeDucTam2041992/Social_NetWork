package com.example.demo.service.comment;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.service.IService;

public interface CommentService extends IService<Comment> {
    Iterable<Comment> findAllByPost(Post post);
}
