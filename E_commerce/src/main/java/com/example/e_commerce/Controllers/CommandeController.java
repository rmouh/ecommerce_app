package com.example.e_commerce.Controllers;


import com.example.e_commerce.Models.Commande;
import com.example.e_commerce.Services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cmd")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    // Récupérer toutes les commandes
    @GetMapping
    public List<Commande> getAllCommandes() {
        return commandeService.getAllCommandes();
    }

    // Récupérer une commande par ID
    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        Optional<Commande> commande = commandeService.getCommandeById(id);
        return commande.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer une nouvelle commande
    @PostMapping("/create/{utilisateurId}")
    public ResponseEntity<Commande> createCommande(@PathVariable Long utilisateurId) {
        Commande nouvelleCommande = commandeService.createCommande(utilisateurId);
        return ResponseEntity.ok(nouvelleCommande);
    }

    // Mettre à jour le statut d'une commande
    @PutMapping("/{id}/statut/{nouveauStatut}")
    public ResponseEntity<String> updateStatut(@PathVariable Long id, @PathVariable String nouveauStatut) {
        String result = commandeService.updateStatut(id, nouveauStatut);
        return ResponseEntity.ok(result);
    }

    // Annuler une commande
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<String> cancelCommande(@PathVariable Long id) {
        String result = commandeService.cancelCommande(id);
        return ResponseEntity.ok(result);
    }

    // Récupérer les commandes d'un utilisateur
    @GetMapping("/utilisateur/{utilisateurId}")
    public List<Commande> getCommandesByUtilisateurId(@PathVariable Long utilisateurId) {
        return commandeService.getCommandesByUtilisateurId(utilisateurId);
    }
}
