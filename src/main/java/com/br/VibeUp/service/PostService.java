package com.br.VibeUp.service;

import com.br.VibeUp.dto.PostDTO;
import com.br.VibeUp.model.Post;
import com.br.VibeUp.model.User;
import com.br.VibeUp.repositories.PostRepository;
import com.br.VibeUp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public PostDTO createPost(String username, PostDTO postDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(postDTO.title());
        post.setLikes(postDTO.likes());
        post.setDateOfPost(new Date());
        post.setFileUrl(postDTO.fileUrl());
        post.setUser(user);

        post = postRepository.save(post);
        return convertToDTO(post);
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private PostDTO convertToDTO(Post post) {
        return new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getLikes(),
                post.getDateOfPost(),
                post.getUser().getId(),
                post.getUser().getUsername(),
                post.getFileUrl()
        );
    }


    public PostDTO getPostWithImage(String postId) {
        Post post = postRepository.findById(String.valueOf(postId))
                .orElseThrow(() -> new RuntimeException("Post not found"));
        String imageUrl = "/posts/image?fileUrl=" + post.getFileUrl();
        return new PostDTO(post.getId(), post.getTitle(), post.getLikes(), post.getDateOfPost(), post.getUser().getId(), post.getUser().getUsername(), imageUrl);
    }
}