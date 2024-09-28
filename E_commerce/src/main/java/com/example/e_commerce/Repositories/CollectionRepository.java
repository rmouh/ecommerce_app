package com.example.e_commerce.Repositories;

import com.example.e_commerce.Models.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    // Méthode pour trouver une collection par son nom
    List<Collection> findByName(String name);

    // Méthode pour récupérer toutes les collections avec un nombre de produits supérieur à une certaine valeur
    List<Collection> findByNbProductsGreaterThan(Long nbProducts);

    // Méthode pour récupérer toutes les collections (juste pour exemple)
    List<Collection> findAll();

    // Méthode pour récupérer toutes les collections (juste pour exemple)
    Optional<Collection> findById(Long id);
}
