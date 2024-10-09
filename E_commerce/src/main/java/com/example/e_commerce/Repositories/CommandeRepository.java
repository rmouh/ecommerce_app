package com.example.e_commerce.Repositories;


import com.example.e_commerce.Models.Commande;
import com.example.e_commerce.Models.Order;
import com.example.e_commerce.Models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByUtilisateurId(Long utilisateurId);
    // Vous pouvez ajouter des méthodes personnalisées si nécessaire
}

