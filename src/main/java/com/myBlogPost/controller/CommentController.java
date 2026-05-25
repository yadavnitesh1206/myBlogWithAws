package com.myBlogPost.controller;

import com.myBlogPost.payload.CommentDto;
import com.myBlogPost.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("postId")Long postId, @RequestBody CommentDto dto)  {
       return new ResponseEntity<>( commentService.createComment(postId,dto), HttpStatus.CREATED);
    }
    @GetMapping("posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable("postId") long postId){
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId),HttpStatus.OK);
    }
    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,@PathVariable("commentId")Long commentId){
       return new ResponseEntity<>( commentService.getCommentById(postId,commentId),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId")Long postId,
                                                    @PathVariable("id")Long commentId,
                                                    @RequestBody CommentDto dto){
        return new ResponseEntity<>( commentService.updateComment(postId,commentId,dto),HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId")Long postId,
                                                @PathVariable("id")Long commentId){
        commentService.deleteComment(postId,commentId);
        return  ResponseEntity.ok("Comment Successfully deleted!");
    }

}
