package com.example.projectcode.Services;


import com.example.projectcode.Models.UserApp;
import com.example.projectcode.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    /*

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //get all the users from UserRepo
    public List<UserApp> getAllUsers() {
        return userRepository.findAll();
    }

    //Get the user by id orr nullif not found
    public Optional<UserApp> getUserById(Long id) {
        return userRepository.findById(id);
    }
    //Add user in the DataBase if new

    public UserApp registerUser(UserApp user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("L'email est déjà utilisé.");
        }
        UserApp newUser = new UserApp(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getAddress(), user.getUsername());
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }







*/








}
