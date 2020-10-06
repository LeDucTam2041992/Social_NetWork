package com.example.demo.service.relationship;

import com.example.demo.model.Relationship;
import com.example.demo.model.User;
import com.example.demo.service.IService;

public interface RelationshipService extends IService<Relationship> {
    Iterable<Relationship> findAllFriendOfUser(User user);
    Iterable<Relationship> findAllBlockOfUser(User user);
    Iterable<Relationship> findAllPendingOfUser(User user);
    Iterable<Relationship> findAllFollowOfUser(User user);
}
