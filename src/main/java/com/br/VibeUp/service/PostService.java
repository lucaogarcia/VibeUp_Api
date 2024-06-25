package com.br.VibeUp.service;

import com.br.VibeUp.dto.PostDTO;
import com.br.VibeUp.model.Post;
import com.br.VibeUp.model.User;
import com.br.VibeUp.repositories.PostRepository;
import com.br.VibeUp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3Service s3Service;

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PostDTO createPost(String username, PostDTO postDTO, MultipartFile file) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = new Post();
        post.setDescription(postDTO.description());
        post.setName(postDTO.name());
        post.setLikes(postDTO.likes());
        post.setDateOfPost(new Date());
        post.setUser(user);

        // Upload file to S3 and set the file URL
        try {
            String fileUrl = s3Service.uploadFile(file);
            post.setFileUrl(fileUrl);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }

        post = postRepository.save(post);
        return convertToDTO(post);
    }

    private PostDTO convertToDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getDescription(),
                post.getName(),
                post.getLikes(),
                post.getDateOfPost(),
                post.getUser().getId(),
                post.getFileUrl() // Include the file URL in the response
        );
    }
}
