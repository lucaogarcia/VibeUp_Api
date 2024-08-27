package com.br.VibeUp.service;

import com.br.VibeUp.dto.PostDTO;
import com.br.VibeUp.model.Post;
import com.br.VibeUp.model.User;
import com.br.VibeUp.repositories.PostRepository;
import com.br.VibeUp.repositories.UserRepository;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private FirebaseApp firebaseApp;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${firebase.bucket.name}")
    private String bucketName;

    public PostDTO createPost(String username, PostDTO postDTO, MultipartFile imageFile) throws IOException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setDescription(postDTO.description());
        post.setName(postDTO.name());
        post.setLikes(postDTO.likes());
        post.setDateOfPost(new Date());
        post.setUser(user);

        String imageUrl = uploadImageToFirebase(imageFile);
        post.setFileUrl(imageUrl);

        post = postRepository.save(post);
        return convertToDTO(post);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private String uploadImageToFirebase(MultipartFile imageFile) throws IOException {
        String fileName = UUID.randomUUID() + "-" + imageFile.getOriginalFilename();
        StorageClient.getInstance(firebaseApp).bucket(bucketName).create(fileName, imageFile.getInputStream(), imageFile.getContentType());
        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }

    private PostDTO convertToDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getDescription(),
                post.getName(),
                post.getLikes(),
                post.getDateOfPost(),
                post.getUser().getId(),
                post.getFileUrl()
        );
    }
}