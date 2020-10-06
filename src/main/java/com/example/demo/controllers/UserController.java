package com.example.demo.controllers;

import com.example.demo.model.*;
import com.example.demo.service.comment.CommentService;
import com.example.demo.service.image.ImageService;
import com.example.demo.service.like.LikeService;
import com.example.demo.service.post.PostService;
import com.example.demo.service.relationship.RelationshipService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/users")
public class UserController {
    @Value("${upload.file}")
    private String pathFile;
    @Autowired
    UserService userService;
    @Autowired
    PostService postService;
    @Autowired
    ImageService imageService;
    @Autowired
    CommentService commentService;
    @Autowired
    LikeService likeService;
    @Autowired
    RelationshipService relationshipService;

    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    private User getUser() {
        return userService.findUserByEmail(getPrincipal());
    }

    private Set<User> getFriends(){
        User user = getUser();
        Iterable<Relationship> friendsRe = relationshipService.findAllFriendOfUser(user);
        Set<User> friends = new LinkedHashSet<>();
        for (Relationship r:friendsRe) {
            if(r.getId().getUser_one_id() == user.getId()) {
                friends.add(userService.findById(r.getId().getUser_two_id()));
            }else {
                friends.add(userService.findById(r.getId().getUser_one_id()));
            }
        }
        return friends;
    }

    @GetMapping("/home")
    public String goHome(){
        return "user/home";
    }


    @GetMapping("/create-post")
    public String createNewPost(Model model){
        model.addAttribute("post", new Post());
        return "post/create";
    }

    @PostMapping("/create")
    public String saveNewPost(@ModelAttribute("post") Post post) {
        Image image = post.getImage();
        if(image!=null) {
            MultipartFile file = image.getFile();
            String img = file.getOriginalFilename();
            image.setName(img);
            imageService.save(image);
            try {
                FileCopyUtils.copy(file.getBytes(), new File(pathFile + img));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        User user = getUser();
        post.setUser(user);
        post.setStatusPost(new StatusPost(1,"Accepted"));
        postService.save(post);
        return "redirect:/users/views-post";
    }

    @GetMapping("/views-post")
    public String viewPost(Model model) {
        Set<User> friends = getFriends();
        User user = getUser();
        model.addAttribute("friends", friends);
        List<Post> postList = new LinkedList<>();

        Iterable<Post> postOfPriority = postService.findAllPostByStatus(new StatusPost(2,"OnTop"));
        for (Post p:postOfPriority) {
            postList.add(p);
        }

        Iterable<Post> postOfUser = postService.findAllPostByAuthor(user, new StatusPost(1, "Accepted"));
        for (Post p:postOfUser) {
            if(!postList.contains(p)) {
                postList.add(p);
            }
        }

        for (User u:friends) {
            Iterable<Post> postOfFriends = postService.findAllPostByAuthor(u, new StatusPost(1, "Accepted"));
            for (Post p:postOfFriends) {
                if(!postList.contains(p)) {
                    postList.add(p);
                }
            }
        }

        for (Post p: postList) {
            Iterable<Comment> comments = commentService.findAllByPost(p);
            Set<Comment> cms = new LinkedHashSet<>();
            for (Comment c:comments) {
                cms.add(c);
            }
            p.setComments(cms);

            Iterable<Like> likes = likeService.findAllByPost(p);
            Set<Like> lks = new LinkedHashSet<>();
            for (Like l:likes) {
                lks.add(l);
            }
            p.setLikes(lks);
        }
        model.addAttribute("posts", postList);
        model.addAttribute("userId",user.getId());
        return "user/viewPost";
    }

    @GetMapping("/post/{id}/delete")
    public String deletePost(@PathVariable("id") long id) {
        Post post = postService.findById(id);
        postService.remove(id);
        return "redirect:/users/views-post";
    }
}
