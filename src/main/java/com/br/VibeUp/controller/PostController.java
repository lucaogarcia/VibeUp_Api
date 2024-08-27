package com.br.VibeUp.controller;

import com.br.VibeUp.dto.PostDTO;
import com.br.VibeUp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                                              @RequestPart("post") PostDTO postDTO,
                                              @RequestPart("file") MultipartFile file) throws IOException {
        PostDTO newPost = postService.createPost(username, postDTO, file);
        return ResponseEntity.ok(newPost);
    }
}
