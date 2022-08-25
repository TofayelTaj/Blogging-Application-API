package com.example.bloggingapplicationapi.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface ImageService {

    void uploadImage(String path, MultipartFile image) throws IOException;
    InputStream serveImage(String path, String imageName) throws FileNotFoundException;



}
