package com.example.projectcode.Repositories;


import com.example.projectcode.Models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long> {
    public Optional<UserApp> findByEmail(String email);
    public Optional<UserApp> findByUsername(String username);
}
