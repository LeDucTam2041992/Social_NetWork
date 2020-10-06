package com.example.demo.repository;

import com.example.demo.model.Relationship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RelationshipRepository extends PagingAndSortingRepository<Relationship,Long> {
    @Query("select r from Relationship r where (r.id.user_one_id=:id or r.id.user_two_id=:id) and r.statusRelation.id=1")
    Iterable<Relationship> findAllFriendsOfUserByUserId(@Param("id") long id);

    @Query("select r from Relationship r where (r.id.user_one_id=:id or r.id.user_two_id=:id) and r.statusRelation.id=4")
    Iterable<Relationship> findAllBlocksOfUserByUserId(@Param("id") long id);

    @Query("select r from Relationship r where (r.id.user_one_id=:id or r.id.user_two_id=:id) and r.statusRelation.id=0")
    Iterable<Relationship> findAllPendingOfUserByUserId(@Param("id") long id);

    @Query("select r from Relationship r where (r.id.user_one_id=:id or r.id.user_two_id=:id) and r.statusRelation.id=3")
    Iterable<Relationship> findAllFollowOfUserByUserId(@Param("id") long id);
}
