package com.example.e_commerce.Services;

import com.example.e_commerce.Models.Product;
import com.example.e_commerce.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Méthode pour ajouter un produit
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
}