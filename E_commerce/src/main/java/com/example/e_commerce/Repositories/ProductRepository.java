package com.example.e_commerce.Repositories;

import com.example.e_commerce.Models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.e_commerce.Models.Product;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCollectionId(Long collectionId); // Méthode pour trouver les produits par collection_id

}
