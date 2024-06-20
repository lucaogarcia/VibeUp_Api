package com.br.VibeUp.repositories;

import com.br.VibeUp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
<<<<<<< HEAD

    Optional<User> findByUsername(String username);
=======
>>>>>>> 7727549a27ae012491d9eeb442d8341d091ac308
}
