package com.example.demo.model;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    @Transient
    private MultipartFile file;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date dateUp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Date getDateUp() {
        return dateUp;
    }

    public void setDateUp(Date dateUp) {
        this.dateUp = dateUp;
    }
}
