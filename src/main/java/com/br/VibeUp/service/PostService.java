package com.br.VibeUp.service;

import com.br.VibeUp.dto.PostDTO;
import com.br.VibeUp.model.Post;
import com.br.VibeUp.model.User;
import com.br.VibeUp.repositories.PostRepository;
import com.br.VibeUp.repositories.UserRepository;
import com.google.cloud.storage.Blob;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public PostDTO createPost(String username, PostDTO postDTO, byte[] imageFile, String originalFileName) throws IOException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setDescription(postDTO.description());
        post.setName(postDTO.name());
        post.setLikes(postDTO.likes());
        post.setDateOfPost(new Date());
        post.setUser(user);

        String fileName = UUID.randomUUID() + originalFileName.substring(originalFileName.lastIndexOf("."));
        String imageUrl = uploadImageToFirebase(imageFile, fileName);
        post.setFileUrl(imageUrl);

        post = postRepository.save(post);
        return convertToDTO(post);
    }


    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private String uploadImageToFirebase(byte[] imageFile, String fileName) throws IOException {
        String contentType = detectMimeTypeByName(fileName);
        System.out.println("Uploading with type: " + contentType);
        StorageClient.getInstance(firebaseApp).bucket(bucketName).create(fileName, imageFile, contentType);
        return "https://storage.googleapis.com/" + bucketName + "/" + fileName;
    }


    private String detectMimeTypeByName(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        }
        throw new IllegalArgumentException("Unsupported file type: " + fileName);
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

    public Blob downloadImageBlobFromFirebase(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        Blob blob = StorageClient.getInstance().bucket(bucketName).get(fileName);
        if (blob == null) {
            throw new RuntimeException("Image not found in Firebase Storage");
        }
        return blob;
    }

    public PostDTO getPostWithImage(String postId) {
        Post post = postRepository.findById(String.valueOf(postId))
                .orElseThrow(() -> new RuntimeException("Post not found"));
        String imageUrl = "/posts/image?fileUrl=" + post.getFileUrl();
        return new PostDTO(post.getId(), post.getDescription(), post.getName(), post.getLikes(), post.getDateOfPost(), post.getUser().getId(), imageUrl);
    }
}