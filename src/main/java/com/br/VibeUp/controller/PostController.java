package com.br.VibeUp.controller;

import com.br.VibeUp.dto.PostDTO;
import com.br.VibeUp.service.PostService;
import com.google.cloud.storage.Blob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
        String originalFileName = file.getOriginalFilename();
        byte[] imageFile = file.getBytes();
        PostDTO newPost = postService.createPost(username, postDTO, imageFile, originalFileName);
        return ResponseEntity.ok(newPost);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable String id) {
        PostDTO postDTO = postService.getPostWithImage(id);
        return ResponseEntity.ok(postDTO);
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getImage(@RequestParam String fileUrl) {
        Blob blob = postService.downloadImageBlobFromFirebase(fileUrl);

        MediaType contentType = MediaType.IMAGE_JPEG;
        if (blob.getContentType() != null) {
            contentType = MediaType.parseMediaType(blob.getContentType());
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + blob.getName() + "\"")
                .contentType(contentType)
                .body(blob.getContent());
    }
}