package com.example.demo.service.post;

import com.example.demo.model.Post;
import com.example.demo.model.StatusPost;
import com.example.demo.model.User;
import com.example.demo.service.IService;

public interface PostService extends IService<Post> {
    Iterable<Post> findAllPostByAuthor(User user, StatusPost statusPost);
    Iterable<Post> findAllPostByStatus(StatusPost statusPost);
}
