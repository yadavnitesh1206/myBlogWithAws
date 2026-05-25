package com.myBlogPost.service.impl;


import com.myBlogPost.entity.Post;
import com.myBlogPost.exception.ResourceNotFoundException;
import com.myBlogPost.payload.PostDto;
import com.myBlogPost.payload.PostResponse;
import com.myBlogPost.repository.PostRepository;
import com.myBlogPost.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;
    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper){
        this.postRepository=postRepository;
        this.modelMapper=modelMapper;
    }

    public PostDto createPost(PostDto postDto) {
      return modelMapper.map( (postRepository.save(modelMapper.map(postDto,Post.class))),PostDto.class);

    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
//        List<Post> posts = postRepository.findAll();
//       return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    // using pagination
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> all = postRepository.findAll(pageable);
        List<Post> content = all.getContent();
//        List<PostDto> postDtoList = content.stream().map(x -> mapToDto(x)).collect(Collectors.toList());

        List<PostDto> postDtoList = content.stream().map(x -> modelMapper.map(x,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setLast(all.isLast());
        postResponse.setPageNo(all.getNumber());
        postResponse.setTotalPages(all.getTotalPages());
        postResponse.setTotalElements(all.getTotalElements());
        postResponse.setPageSize(all.getSize());
        return postResponse;


    }

    @Override
    public void deleteById(Long id)  {
        postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Id",id));
        postRepository.deleteById(id);
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto dto)  {
        Post post = postRepository.findById(dto.getId()).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", dto.getId()));
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());
        Post save = postRepository.save(post);
        return modelMapper.map(save,PostDto.class);

    }


    //convert Dto to entity
    public Post mapToEntity(PostDto postDto){
        Post post=new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
return post;
    }
    // convert entity to dto
    public PostDto mapToDto(Post post){
        PostDto dto=new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setDescription(post.getDescription());
        return dto;
    }

}
