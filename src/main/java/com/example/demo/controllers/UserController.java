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
import org.springframework.http.MediaType;
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
    public User getUser() {
        return userService.findUserByEmail(getPrincipal());
    }

    private Set<User> getFriends(User user){
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

    private Set<User> getFriendsOnline(User user) {
        Set<User> friends = getFriends(user);
        Set<User> fiendsOnline = new LinkedHashSet<>();
        for (User u:friends) {
            if(u.getStatusUser().getStatus().equals("online")) fiendsOnline.add(u);
        }
        return fiendsOnline;
    }

    private void setLikeForPost(Post post) {
        Iterable<Like> likes = likeService.findAllByPost(post);
        Set<Like> lks = new LinkedHashSet<>();
        for (Like l:likes) {
            lks.add(l);
        }
        post.setLikes(lks);
    }

    private void setCommentForPost(Post post) {
        Iterable<Comment> comments = commentService.findAllByPost(post);
        Set<Comment> cms = new LinkedHashSet<>();
        for (Comment c:comments) {
            cms.add(c);
        }
        post.setComments(cms);
    }

    private void saveImage(Image image, User user){
        MultipartFile file = image.getFile();
        String img = null;
        if (!file.isEmpty()) {
            boolean check = true;
            String imgName = file.getOriginalFilename();
            while (check) {
                Random rd = new Random();
                img = rd.nextInt(100000) + imgName.substring(imgName.length()-4);
                image.setName(img);
                image.setUser(user);
                try {
                    imageService.save(image);
                    check=false;
                }catch (Exception e){
                    e.printStackTrace();
                    check=true;
                }
            }
            try {
                FileCopyUtils.copy(file.getBytes(), new File(pathFile + img));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/home")
    public String goHome(Model model){
        User user = getUser();
        System.out.println(user.getId());
        System.out.println(user.getStatusUser().getStatus());
        List<Post> postList = new LinkedList<>();
        Iterable<Post> postOfUser = postService.findAllPostByAuthor(user, new StatusPost(1,"Accepted"));
        for (Post p:postOfUser) {
            postList.add(p);
        }
        for (Post p:postList) {
            setCommentForPost(p);
            setLikeForPost(p);
        }
        Set<User> friends = getFriendsOnline(user);
        Iterable<Relationship> relationships = relationshipService.findAllPendingOfUser(user);
        if(relationships!=null) {
            List<User> userPending = new LinkedList<>();
            for (Relationship r : relationships) {
                if(r.getUser().getId() != user.getId()) {
                    if (r.getId().getUser_one_id() == user.getId()) {
                        userPending.add(userService.findById(r.getId().getUser_two_id()));
                    } else {
                        userPending.add(userService.findById(r.getId().getUser_one_id()));
                    }
                }
            }
            model.addAttribute("pending", userPending);
        }
        model.addAttribute("friends", friends);
        model.addAttribute("posts", postList);
        model.addAttribute("user",user);
        return "user/home";
    }


    @GetMapping("/create-post")
    public String createNewPost(Model model){
        model.addAttribute("post", new Post());
        return "post/create";
    }

    @PostMapping("/create")
    public String saveNewPost(@ModelAttribute("post") Post post) {
        User user = getUser();
        Image image = post.getImage();
        MultipartFile file = image.getFile();
        if(!file.isEmpty()) {
            boolean check = true;
            String img = null;
            String imgName = file.getOriginalFilename();
            while (check) {
                Random rd = new Random();
                img = rd.nextInt(100000) + imgName.substring(imgName.length()-4);
                image.setName(img);
                try {
                    imageService.save(image);
                    check=false;
                }catch (Exception e){
                    e.printStackTrace();
                    check=true;
                }
            }
            try {
                FileCopyUtils.copy(file.getBytes(), new File(pathFile + img));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            imageService.save(image);
        }
        post.setUser(user);
        post.setStatusPost(new StatusPost(1,"Accepted"));
        postService.save(post);
        return "redirect:/users/views-post";
    }

    @GetMapping("/views-post")
    public String viewPost(Model model) {
        User user = getUser();
        Set<User> friendsOnline = getFriendsOnline(user);
        Set<User> friends = getFriends(user);
        model.addAttribute("friends", friendsOnline);
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
            setCommentForPost(p);
            setLikeForPost(p);
        }
        model.addAttribute("posts", postList);
        model.addAttribute("user", user);
        model.addAttribute("userId",user.getId());
        return "user/viewPost";
    }

    @GetMapping("/post/{id}/delete")
    public String deletePost(@PathVariable("id") long id) {
        Post post = postService.findById(id);
        User user = getUser();
        if(post.getUser().getId() == user.getId()) {
            post.setStatusPost(new StatusPost(0, "Block"));
            postService.save(post);
        }
        return "redirect:/users/views-post";
    }

    @GetMapping(value = "/like-post/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int likeStatus(@PathVariable long id) {
        Post post = postService.findById(id);
        User user = getUser();
        setLikeForPost(post);
        long statusLikeOfUser = post.getStatusLikeOfUser(user.getId());
        if(statusLikeOfUser==0||statusLikeOfUser==2){
            Like like = new Like();
            like.setId(new CompositeLikePK(user.getId(), post.getId()));
            like.setStatusLike(new StatusLike(1,"like"));
            likeService.save(like);
            return 1;
        }else {
            Like like = new Like();
            like.setId(new CompositeLikePK(user.getId(), post.getId()));
            like.setStatusLike(new StatusLike(0,"none"));
            likeService.save(like);
            return 0;
        }
    }

    @GetMapping(value = "/dislike-post/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int dislikeStatus(@PathVariable long id) {
        Post post = postService.findById(id);
        User user = getUser();
        setLikeForPost(post);
        long statusLikeOfUser = post.getStatusLikeOfUser(user.getId());
        if(statusLikeOfUser==0||statusLikeOfUser==1){
            Like like = new Like();
            like.setId(new CompositeLikePK(user.getId(), post.getId()));
            like.setStatusLike(new StatusLike(2,"dislike"));
            likeService.save(like);
            return 2;
        }else {
            Like like = new Like();
            like.setId(new CompositeLikePK(user.getId(), post.getId()));
            like.setStatusLike(new StatusLike(0,"none"));
            likeService.save(like);
            return 0;
        }
    }

    @PostMapping(value = "/comment-post/{id}")
    public String comment(@PathVariable long id, @ModelAttribute("comment") String cms) {
        Post post = postService.findById(id);
        User user = getUser();
        Comment comment = new Comment();
        comment.setComment(cms);
        comment.setPost(post);
        System.out.println(user.getId());
        comment.setUser(user);
        commentService.save(comment);
        return "redirect:/users/views-post";
    }

    @GetMapping("/views-images")
    public String showImages(Model model) {
        User user = getUser();
        Iterable<Image> images = imageService.findAllByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("imageList", images);
        model.addAttribute("image", new Image());
        return "user/images_list";
    }

    @PostMapping("/create-image")
    public String createImg(@ModelAttribute("image") Image image) {
        User user = getUser();
        saveImage(image,user);
        return "redirect:/users/views-images";
    }

    @GetMapping("/views-friends")
    public String listFriends(Model model){
        User user = getUser();
        model.addAttribute("user",user);
        model.addAttribute("friends", getFriends(user));
        return "user/friends_list";
    }

    @GetMapping("/go-home-user/{id}")
    public String goHomeUser(@PathVariable("id") long id, Model model) {
        User user = getUser();
        Set<User> friendsOfUser = getFriends(user);
        User userA = userService.findById(id);
        Set<User> friendsOfUserA = getFriends(userA);
        Iterable<Image> imagesOfUserA = imageService.findAllByUser(userA);
        boolean relationship;
        Iterable<Post> postOfUserA = postService.findAllPostByAuthor(userA, new StatusPost(1, "Accepted"));
        if(friendsOfUser.contains(userA)){
            relationship = true;
        } else {
            relationship = false;
        }
        model.addAttribute("userA", userA);
        model.addAttribute("pOA", postOfUserA);
        model.addAttribute("fOA", friendsOfUserA);
        model.addAttribute("iOA", imagesOfUserA);
        model.addAttribute("relation", relationship);
        model.addAttribute("user", user);
        return "user/guest";
    }

    @PostMapping("/search-user")
    public String findUser(@ModelAttribute("search") String str, Model model){
        User user = getUser();
        List<User> userFind = (List<User>) userService.findAllByName(str);
        List<User> userBlock = new LinkedList<>();
        if(!userFind.isEmpty()){
            for (User u:userFind) {
                if(user.getId()<u.getId()){
                    Relationship relationship = relationshipService.findRelationshipOfTwoUser(user,u);
                    if(relationship!=null){
                        if(relationship.getStatusRelation().getId()==4) userBlock.add(u);
                    }
                } else {
                    Relationship relationship = relationshipService.findRelationshipOfTwoUser(u,user);
                    if(relationship!=null){
                        if(relationship.getStatusRelation().getId()==4) userBlock.add(u);
                    }
                }
            }
        }
        userFind.removeAll(userBlock);
        model.addAttribute("userFind", userFind);
        return "user/search";
    }

    @GetMapping("/add-friend/{id}")
    public String addFriends(@PathVariable("id") long id) {
        User user = getUser();
        User userB = userService.findById(id);
        Relationship relationship = new Relationship();
        CompositeRelationshipPK compositeRelationshipPK = new CompositeRelationshipPK();
        if(user.getId()<userB.getId()) {
            compositeRelationshipPK.setUser_one_id(user.getId());
            compositeRelationshipPK.setUser_two_id(userB.getId());
        } else {
            compositeRelationshipPK.setUser_one_id(userB.getId());
            compositeRelationshipPK.setUser_two_id(user.getId());
        }
        relationship.setId(compositeRelationshipPK);
        relationship.setStatusRelation(new StatusRelation(0,"Pending"));
        relationship.setUser(user);
        relationshipService.save(relationship);
        return "redirect:/users/home";
    }

    @GetMapping("/agree-make-friend/{id}")
    public String agreeFriend(@PathVariable("id") long id){
        User user = getUser();
        User userB = userService.findById(id);
        Relationship relationship = new Relationship();
        if(user.getId()<userB.getId()) {
            relationship = relationshipService.findRelationshipOfTwoUser(user, userB);
        }else {
            relationship = relationshipService.findRelationshipOfTwoUser(userB, user);
        }
        relationship.setStatusRelation(new StatusRelation(1,"Accepted"));
        relationship.setUser(user);
        relationshipService.save(relationship);
        return "redirect:/users/home";
    }

    @GetMapping("/avatar-change")
    public String changeAvatar(Model model){
        User user = getUser();
        Iterable<Image> images = imageService.findAllByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("imageList", images);
        return "user/choose_image";
    }

    @GetMapping("/avatar-change/{id}")
    public String saveAvatar(@PathVariable("id") long id){
        User user = getUser();
        Image image = imageService.findById(id);
        user.setAvatar(image);
        userService.save(user);
        return "redirect:/users/home";
    }
}

