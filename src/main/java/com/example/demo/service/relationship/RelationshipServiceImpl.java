package com.example.demo.service.relationship;

import com.example.demo.model.Relationship;
import com.example.demo.model.User;
import com.example.demo.repository.RelationshipRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RelationshipServiceImpl implements RelationshipService {

    @Autowired
    RelationshipRepository relationshipRepository;
    @Override
    public Iterable<Relationship> findAll() {
        return relationshipRepository.findAll();
    }

    @Override
    public Relationship findById(long id) {
        return relationshipRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Relationship model) {
        relationshipRepository.save(model);
    }

    @Override
    public void remove(long id) {
        relationshipRepository.deleteById(id);
    }

    @Override
    public Iterable<Relationship> findAllFriendOfUser(User user) {
        return relationshipRepository.findAllFriendsOfUserByUserId(user.getId());
    }

    @Override
    public Iterable<Relationship> findAllBlockOfUser(User user) {
        return relationshipRepository.findAllBlocksOfUserByUserId(user.getId());
    }

    @Override
    public Iterable<Relationship> findAllPendingOfUser(User user) {
        return relationshipRepository.findAllPendingOfUserByUserId(user.getId());
    }

    @Override
    public Iterable<Relationship> findAllFollowOfUser(User user) {
        return relationshipRepository.findAllFollowOfUserByUserId(user.getId());
    }
}
