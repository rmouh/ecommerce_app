package com.example.e_commerce.Repositories;

import com.example.e_commerce.Models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.e_commerce.Models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
