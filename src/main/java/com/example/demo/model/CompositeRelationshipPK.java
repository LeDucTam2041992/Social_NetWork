package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CompositeRelationshipPK implements Serializable {
    @Column
    private long user_one_id;
    @Column
    private long user_two_id;

    public CompositeRelationshipPK() {
    }

    public long getUser_one_id() {
        return user_one_id;
    }

    public void setUser_one_id(long user_one_id) {
        this.user_one_id = user_one_id;
    }

    public long getUser_two_id() {
        return user_two_id;
    }

    public void setUser_two_id(long user_two_id) {
        this.user_two_id = user_two_id;
    }
}
