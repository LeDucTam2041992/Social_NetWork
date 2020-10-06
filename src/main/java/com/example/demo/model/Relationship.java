package com.example.demo.model;

import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Table(name = "relationships")
public class Relationship {
    @EmbeddedId
    @Column(nullable = false)
    private CompositeRelationshipPK id;

    @ManyToOne()
    @JoinColumn(name = "status")
    private StatusRelation statusRelation;

    @ManyToOne()
    @JoinColumn(name = "action_user_id")
    private User user;

    public Relationship() {
    }

    public CompositeRelationshipPK getId() {
        return id;
    }

    public void setId(CompositeRelationshipPK id) {
        this.id = id;
    }

    public StatusRelation getStatusRelation() {
        return statusRelation;
    }

    public void setStatusRelation(StatusRelation statusRelation) {
        this.statusRelation = statusRelation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
