package com.example.demo;

import com.example.demo.sercurity.CustomLogoutSuccessHandler;
import com.example.demo.service.comment.CommentService;
import com.example.demo.service.comment.CommentServiceImpl;
import com.example.demo.service.image.ImageService;
import com.example.demo.service.image.ImageServiceImpl;
import com.example.demo.service.like.LikeService;
import com.example.demo.service.like.LikeServiceImpl;
import com.example.demo.service.post.PostService;
import com.example.demo.service.post.PostServiceImpl;
import com.example.demo.service.relationship.RelationshipService;
import com.example.demo.service.relationship.RelationshipServiceImpl;
import com.example.demo.service.user.UserService;
import com.example.demo.service.user.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@SpringBootApplication
public class DemoApplication {
    @Bean
    public UserService userService(){
        return new UserServiceImpl();
    }

    @Bean
    public PostService postService() {
        return new PostServiceImpl();
    }

    @Bean
    public ImageService imageService() {
        return new ImageServiceImpl();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceImpl();
    }

    @Bean
    public LikeService likeService() {
        return new LikeServiceImpl();
    }

    @Bean
    public RelationshipService relationshipService() {
        return new RelationshipServiceImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
