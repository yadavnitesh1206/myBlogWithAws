package com.myBlogPost.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws IOException {

        String fileName=file.getOriginalFilename()+"_"+System.currentTimeMillis();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName)
                .key(fileName).contentType(file.getContentType()).build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        return fileName;
//        return getFileUrl(fileName);
    }
    public String getFileUrl(String fileName) {

        return "https://" + bucketName +
                ".s3.amazonaws.com/" + fileName;
    }
}
