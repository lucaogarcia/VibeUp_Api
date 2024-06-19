package com.br.VibeUp.service;

import com.br.VibeUp.dto.UserDTO;
import com.br.VibeUp.model.User;
import com.br.VibeUp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getDate_of_birth(),
                user.isAdmin(),
                user.getLikes(),
                user.getCourse()
        );
    }
}
