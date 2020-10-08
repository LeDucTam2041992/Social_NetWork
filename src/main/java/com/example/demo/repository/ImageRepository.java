package com.example.demo.repository;

import com.example.demo.model.Image;
import com.example.demo.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {
    Iterable<Image> findAllByUser(User user);
}
