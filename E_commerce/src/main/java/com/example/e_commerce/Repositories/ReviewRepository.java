package com.example.e_commerce.Repositories;

import com.example.e_commerce.Models.Review;
import com.example.e_commerce.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
}
