package com.example.demo.service.image;

import com.example.demo.model.Image;
import com.example.demo.model.User;
import com.example.demo.service.IService;

public interface ImageService extends IService<Image> {
    Iterable<Image> findAllByUser(User user);
}
