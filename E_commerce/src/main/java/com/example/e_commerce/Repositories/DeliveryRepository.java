package com.example.e_commerce.Repositories;

import com.example.e_commerce.Models.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Delivery findByOrderId(Long orderId); // Pour retrouver une livraison par commande
}

