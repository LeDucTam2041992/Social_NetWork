package com.example.demo.service.like;

import com.example.demo.model.Like;
import com.example.demo.model.Post;
import com.example.demo.service.IService;

public interface LikeService extends IService<Like> {
    Iterable<Like> findAllByPost(Post post);

    void updateLike(int status_id, long post_id, long user_id);
}
