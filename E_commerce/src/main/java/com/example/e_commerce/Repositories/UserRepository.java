package com.example.e_commerce.Repositories;
import com.example.e_commerce.Models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long> {
    public UserApp findByEmail(String email);
    public UserApp findByUsername(String username);

}