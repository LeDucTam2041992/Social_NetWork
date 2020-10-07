package com.example.demo.service.like;

import com.example.demo.model.Like;
import com.example.demo.model.Post;
import com.example.demo.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class LikeServiceImpl implements LikeService{
    @Autowired
    LikeRepository likeRepository;
    @Override
    public Iterable<Like> findAllByPost(Post post) {
        return likeRepository.findAllByPostId(post.getId());
    }

    @Override
    public void updateLike(int status_id, long post_id, long user_id) {
        likeRepository.updateLike(status_id,post_id,user_id);
    }

    @Override
    public Iterable<Like> findAll() {
        return likeRepository.findAll();
    }

    @Override
    public Like findById(long id) {
        return likeRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Like model) {
        likeRepository.save(model);
    }

    @Override
    public void remove(long id) {
        likeRepository.deleteById(id);
    }
}
