package com.example.demo.service.image;

import com.example.demo.model.Image;
import com.example.demo.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image findById(long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Image model) {
        imageRepository.save(model);
    }

    @Override
    public void remove(long id) {
        imageRepository.deleteById(id);
    }
}
