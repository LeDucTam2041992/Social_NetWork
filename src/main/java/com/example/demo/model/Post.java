package com.example.demo.model;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    @Column(columnDefinition = "text")
    private String content;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date datePublished;

    @ManyToOne
    @JoinColumn(name = "status")
    private StatusPost statusPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "image_id")
    private Image image;


    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.REMOVE)
    Set<Comment> comments;

    @OneToMany(targetEntity = Like.class, cascade = CascadeType.REMOVE)
    Set<Like> likes;

    public Post() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public StatusPost getStatusPost() {
        return statusPost;
    }

    public void setStatusPost(StatusPost statusPost) {
        this.statusPost = statusPost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public long getNumOfLike(){
        long countLike = 0;
        for (Like l:likes) {
            if(l.getStatusLike().getStatus().equals("like")) countLike++;
        }
        return countLike;
    }

    public long getNumOfDisLike(){
        long countDisLike = 0;
        for (Like l:likes) {
            if(l.getStatusLike().getStatus().equals("dislike")) countDisLike++;
        }
        return countDisLike;
    }

    public String getIntroduce(){
        String str = "";
        if(content.length()<=200){
            str += content;
        }else {
            str = content.substring(0,200) + "...";
        }
        return str;
    }

    public long getStatusLikeOfUser(long userId){
        for (Like l:likes) {
            if(l.getId().getUser_id() == userId && l.getStatusLike().getId()==1) return 1;
            if(l.getId().getUser_id() == userId && l.getStatusLike().getId()==2) return 2;
        }
        return 0;
    }
}