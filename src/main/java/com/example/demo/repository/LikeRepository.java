package com.example.demo.repository;

import com.example.demo.model.Like;
import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends PagingAndSortingRepository<Like, Long> {
    @Query("select lk from Like lk where lk.id.post_id=:id")
    Iterable<Like> findAllByPostId(@Param("id") long id);

    @Query("update Like l set l.statusLike.id=:status_id where l.id.post_id=:post_id and l.id.user_id=:user_id")
    void updateLike(@Param("status_id") int status_id, @Param("post_id") long post_id, @Param("user_id") long user_id);
}
