package com.myBlogPost.service;

import com.myBlogPost.payload.PostDto;
import com.myBlogPost.payload.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int pageNo, int pageSize , String sortBy, String sortDir);
    void deleteById(Long id) ;
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto dto) ;



}
