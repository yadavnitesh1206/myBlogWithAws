package com.myBlogPost.service;

import com.myBlogPost.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto dto);
    List<CommentDto> getCommentsByPostId(Long postId);
    CommentDto getCommentById(Long postId,long CommentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto dto);

    void deleteComment(Long postId, Long commentId);
}
