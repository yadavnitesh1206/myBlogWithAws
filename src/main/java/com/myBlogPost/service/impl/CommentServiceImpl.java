package com.myBlogPost.service.impl;

import com.myBlogPost.entity.Comment;
import com.myBlogPost.entity.Post;
import com.myBlogPost.exception.BlogApiException;
import com.myBlogPost.exception.ResourceNotFoundException;
import com.myBlogPost.payload.CommentDto;
import com.myBlogPost.repository.CommentRepository;
import com.myBlogPost.repository.PostRepository;
import com.myBlogPost.service.CommentService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CommentServiceImpl implements CommentService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;


    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto dto)  {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = modelMapper.map(dto,Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment,CommentDto.class);


    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
      return   comments.stream().map(comment -> modelMapper.map(comment,CommentDto.class)).collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentById(Long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));

        if(!post.getId().equals(comment.getPost().getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to Post");
        }
        return modelMapper.map(comment,CommentDto.class);

    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto dto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to Post");
        }
        comment.setBody(dto.getBody());
        comment.setName(dto.getName());
        comment.setEmail(dto.getEmail());
        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDto.class);


    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Comment does not belongs to Post");
        }
        commentRepository.delete(comment);
        return;


    }

//    public Comment mapToEntity(CommentDto dto){
//        Comment comment=new Comment();
//        comment.setBody(dto.getBody());
//        comment.setName(dto.getName());
//        comment.setEmail(dto.getEmail());
//        comment.setId(dto.getId());
//        return comment;
//    }
//    public CommentDto mapToDto(Comment comment){
//        CommentDto dto=new CommentDto();
//        dto.setBody(comment.getBody());
//        dto.setEmail(comment.getEmail());
//        dto.setName(comment.getName());
//        dto.setId(comment.getId());
//        return dto;
//    }
}
