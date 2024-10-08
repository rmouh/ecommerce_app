package com.example.e_commerce.Services;

import com.example.e_commerce.Models.Delivery;
import com.example.e_commerce.Repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    // Récupérer une livraison par ID de commande
    public Delivery getDeliveryByOrderId(Long orderId) {
        return deliveryRepository.findByOrderId(orderId);
    }

    // Mettre à jour une livraison
    public Delivery updateDelivery(Long id, Delivery updatedDelivery) {
        // Vérifie si la livraison existe
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));

        // Met à jour l'adresse et le statut
        delivery.setAddress(updatedDelivery.getAddress());
        delivery.setStatus(updatedDelivery.getStatus());

        // Enregistrer la livraison mise à jour dans la base de données
        return deliveryRepository.save(delivery);
    }


}
