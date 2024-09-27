package com.example.e_commerce.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @GetMapping("/")
    public  String home(){
        return "Home page";
    }

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
