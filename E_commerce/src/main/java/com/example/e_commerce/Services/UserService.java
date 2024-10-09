package com.example.e_commerce.Services;



import com.example.e_commerce.Models.*;
import com.example.e_commerce.Repositories.CommandeRepository;
import com.example.e_commerce.Repositories.OrderRepository;
import com.example.e_commerce.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repo;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private OrderService orderService;  // Si tu as un service pour les commandes

    public Map<String, Object> getUserProfile(String username) {
        UserApp user = repo.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("User", user);

        // Si tu as une entité Order pour les commandes
        List<Order> orders = orderService.findOrdersByUserId(user.getId());
        response.put("Orders", orders);

        return response;
    }

    //read the user from db based on the username
    //!! can add a findByEmail if needed
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserApp user = repo.findByUsername(username);
        if (user != null){
            var springUser = User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
            return springUser;
        }
        return null;
    }

    // Récupérer l'historique des commandes de l'utilisateur





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
