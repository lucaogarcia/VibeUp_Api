package com.br.VibeUp.controller;

import com.br.VibeUp.dto.PostDTO;
import com.br.VibeUp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/{username}/createPost")
    public ResponseEntity<PostDTO> createPost(@PathVariable String username,
                                              @RequestBody PostDTO postDTO){
        PostDTO newPost = postService.createPost(username, postDTO);
        return ResponseEntity.ok(newPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable String id) {
        PostDTO postDTO = postService.getPostWithImage(id);
        return ResponseEntity.ok(postDTO);
    }

}