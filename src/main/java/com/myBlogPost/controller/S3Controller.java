package com.myBlogPost.controller;

import com.myBlogPost.service.impl.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {
    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public String uploadFIle(@RequestParam("file")MultipartFile multipartFile) throws IOException {
        return s3Service.uploadFile(multipartFile);
    }

}
