package com.myBlogPost.controller;


import com.myBlogPost.payload.PostDto;
import com.myBlogPost.payload.PostResponse;
import com.myBlogPost.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    // create post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto dto){

      return new ResponseEntity<>(  postService.createPost(dto), HttpStatus.CREATED);

    }
    @GetMapping
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNo",defaultValue = "0",required = false)int pageNo,
                                                   @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
                                                   @RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy,
                                                   @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir){
        return  new ResponseEntity<>( postService.getAllPost(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostBYId(@PathVariable("id")Long id) {
       postService.deleteById(id);
       return new ResponseEntity<>("Post deleted successfully.",HttpStatus.OK);
         }
         @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id")Long id)  {
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
         }
         @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto dto){
        return new ResponseEntity<>(postService.updatePost(dto),HttpStatus.OK);
         }


}
