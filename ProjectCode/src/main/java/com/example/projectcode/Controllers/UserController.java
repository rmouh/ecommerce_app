package com.example.projectcode.Controllers;

import com.example.projectcode.Models.UserApp;
import com.example.projectcode.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    //@Autowired
    //private UserService userService;


    @GetMapping("/home")
    public String test(){
        return "Reussi";
    }
    //find all users
    /*@GetMapping
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
