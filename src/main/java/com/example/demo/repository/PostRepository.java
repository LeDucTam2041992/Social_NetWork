package com.example.demo.repository;

import com.example.demo.model.Post;
import com.example.demo.model.StatusPost;
import com.example.demo.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post,Long> {
    Iterable<Post> findAllByStatusPost(StatusPost statusPost);
    Iterable<Post> findAllByUserAndStatusPost(User user, StatusPost statusPost);
}
