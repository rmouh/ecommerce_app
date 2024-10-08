package com.example.e_commerce.Repositories;

import com.example.e_commerce.Models.Order;
import com.example.e_commerce.Models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(UserApp user);

    Optional<Order> findByUserAndStatus(UserApp user, String status);

}
