package com.example.projectcode.Repositories;

import com.example.projectcode.Models.Order;
import com.example.projectcode.Models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(UserApp user);
}
