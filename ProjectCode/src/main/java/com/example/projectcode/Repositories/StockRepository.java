package com.example.projectcode.Repositories;
import com.example.projectcode.Models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    // Ajoutez des méthodes personnalisées ici si nécessaire
    Stock findByProductId(Long productId);
}
