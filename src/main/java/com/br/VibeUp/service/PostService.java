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

    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public PostDTO createPost(String username, PostDTO postDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = new Post();
        post.setDescription(postDTO.description());
        post.setName(postDTO.name());
        post.setLikes(postDTO.likes());
        post.setDateOfPost(new Date()); // Automatically set the current date and time
        post.setUser(user);
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
                post.getUser().getId()
        );
    }
}
