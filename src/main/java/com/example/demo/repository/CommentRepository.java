package com.example.demo.repository;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment,Long> {
    Iterable<Comment> findAllByPost(Post post);
}
