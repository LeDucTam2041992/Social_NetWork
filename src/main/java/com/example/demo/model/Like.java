package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class Like {
    @EmbeddedId
    private CompositeLikePK id;

    @ManyToOne
    @JoinColumn(name = "status")
    private StatusLike statusLike;

    public Like() {
    }

    public CompositeLikePK getId() {
        return id;
    }

    public void setId(CompositeLikePK id) {
        this.id = id;
    }

    public StatusLike getStatusLike() {
        return statusLike;
    }

    public void setStatusLike(StatusLike statusLike) {
        this.statusLike = statusLike;
    }
}
