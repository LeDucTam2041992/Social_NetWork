package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CompositeLikePK implements Serializable {
    @Column
    private long user_id;
    @Column
    private long post_id;
}
