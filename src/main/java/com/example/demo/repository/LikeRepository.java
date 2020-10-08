package com.example.demo.repository;

import com.example.demo.model.Like;
import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends PagingAndSortingRepository<Like, Long> {
    @Query("select lk from Like lk where lk.id.post_id=:id")
    Iterable<Like> findAllByPostId(@Param("id") long id);
}
