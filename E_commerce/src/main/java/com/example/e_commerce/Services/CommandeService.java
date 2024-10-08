package com.example.e_commerce.Services;


import com.example.e_commerce.Models.Commande;
import com.example.e_commerce.Models.Delivery;
import com.example.e_commerce.Repositories.CommandeRepository;
import com.example.e_commerce.Repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {
    @Autowired
    private DeliveryRepository deliveryRepository;


    @Autowired
    private CommandeRepository commandeRepository;


    // Récupérer la liste des commandes
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    // Récupérer une commande par ID
    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    // Créer une nouvelle commande
    public Commande createCommande(Long utilisateurId) {
        Commande nouvelleCommande = new Commande();
        nouvelleCommande.setUtilisateurId(utilisateurId);
        nouvelleCommande.setStatut("en traitement"); // Statut initial
        nouvelleCommande.setDateCommande(LocalDateTime.now());
        return commandeRepository.save(nouvelleCommande);
    }

    // Mettre à jour le statut d'une commande
    public String updateStatut(Long id, String nouveauStatut) {
        Optional<Commande> optionalCommande = commandeRepository.findById(id);
        if (optionalCommande.isPresent()) {
            Commande commande = optionalCommande.get();
            commande.setStatut(nouveauStatut);
            commandeRepository.save(commande);
            return "Statut de la commande mis à jour avec succès";
        } else {
            return "Commande non trouvée";
        }
    }

    // Annuler une commande
    public String cancelCommande(Long id) {
        Optional<Commande> optionalCommande = commandeRepository.findById(id);
        if (optionalCommande.isPresent()) {
            commandeRepository.delete(optionalCommande.get());
            return "Commande annulée avec succès";
        } else {
            return "Commande non trouvée";
        }
    }

    // Récupérer les commandes d'un utilisateur
    public List<Commande> getCommandesByUtilisateurId(Long utilisateurId) {
        return commandeRepository.findByUtilisateurId(utilisateurId);
    }


    // Méthode pour passer une commande en livraison
    public Commande passToDelivery(Long id, String address) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        // Changer le statut de la commande en 'en livraison'
        commande.setStatut("En livraison");

        // Enregistrer la commande mise à jour dans la base de données
        commandeRepository.save(commande);

        // Créer une nouvelle instance de Delivery
        Delivery newDelivery = new Delivery();
        newDelivery.setAddress(address); // L'adresse de livraison passée comme paramètre
        newDelivery.setStatus("En livraison"); // Définir le statut initial

        // Lier la livraison à la commande
        newDelivery.setOrder(commande);

        // Enregistrer la nouvelle livraison dans la base de données
        deliveryRepository.save(newDelivery);

        // Retourner la commande mise à jour
        return commande;
    }
}
