package com.example.e_commerce.Controllers;

import com.example.e_commerce.Models.Delivery;
import com.example.e_commerce.Services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries") // Définir la base de l'URL pour les livraisons
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    // Endpoint pour récupérer les informations de livraison par ID de commande
    @GetMapping("/order/{orderId}")
    public ResponseEntity<String> getDeliveryByOrderId(@PathVariable Long orderId) {
        Delivery delivery = deliveryService.getDeliveryByOrderId(orderId);

        // Vérifier si la livraison existe
        if (delivery != null) {
            return ResponseEntity.ok("Livraison récupérée avec succès: " + delivery.toString());
        } else {
            return ResponseEntity.status(404).body("Aucune livraison trouvée pour l'ID de commande: " + orderId);
        }
    }

    // Endpoint pour mettre à jour les informations de livraison
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDelivery(@PathVariable Long id, @RequestBody Delivery updatedDelivery) {
        try {
            Delivery delivery = deliveryService.updateDelivery(id, updatedDelivery);
            return ResponseEntity.ok("Mise à jour réussie: " + delivery.toString());
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Échec de la mise à jour: " + e.getMessage());
        }
    }
}
