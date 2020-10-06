package com.example.demo.controllers;

import com.example.demo.model.Image;
import com.example.demo.model.User;
import com.example.demo.service.image.ImageService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    UserService userService;
    @Autowired
    ImageService imageService;

    @GetMapping("/create-account")
    public String create(Model model){
        model.addAttribute("user", new User());
        return "user/create";
    }

    @PostMapping("/create-account")
    public String saveNewUser(@ModelAttribute("user") User user) {
        Image image = imageService.findById(0);
        user.setAvatar(image);
        userService.save(user);
        return "index";
    }
}
