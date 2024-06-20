package com.br.VibeUp.repositories;

import com.br.VibeUp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, String> {
}
