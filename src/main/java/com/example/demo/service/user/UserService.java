package com.example.demo.service.user;

import com.example.demo.model.User;
import com.example.demo.service.IService;

public interface UserService extends IService<User> {
    User findUserByEmail(String email);
    Iterable<User> findAllByName(String name);
}
