package com.example.bloggingapplicationapi.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    void uploadImage(String path, MultipartFile image) throws IOException;



}
