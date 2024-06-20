package com.br.VibeUp.controller;

import com.br.VibeUp.dto.PostDTO;
import com.br.VibeUp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{username}/createPost")
    public ResponseEntity<PostDTO> createPost(@PathVariable String username, @RequestBody PostDTO postDTO) {
        PostDTO newPost = postService.createPost(username, postDTO);
        return ResponseEntity.ok(newPost);
    }
}
