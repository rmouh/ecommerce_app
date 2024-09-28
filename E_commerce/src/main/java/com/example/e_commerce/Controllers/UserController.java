package com.example.e_commerce.Controllers;

import com.example.e_commerce.Models.UserApp;
import com.example.e_commerce.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.util.Map;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public  String home(){
        return "Home page";
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getUserProfile(Authentication auth) {
        String username = auth.getName();
        try {
            Map<String, Object> userProfile = userService.getUserProfile(username);
            return ResponseEntity.ok(userProfile);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }




       /*
    @GetMapping
    public ResponseEntity<List<UserApp>> getAllUsers() {
        List<UserApp> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }



    //Find user by Id
    @GetMapping("/{id}")
    public ResponseEntity<UserApp> getUserById(@PathVariable Long id) {
        Optional<UserApp> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
    // Register a new User
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserApp user) {
        try {
            UserApp newUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur enregistré avec succès : " + newUser.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



*/





}
