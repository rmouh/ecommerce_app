package com.example.e_commerce.Models;


import jakarta.persistence.*;

//import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commandes")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "utilisateur_id")
    private Long utilisateurId; // Identifiant de l'utilisateur qui a passé la commande

    @Column(name = "statut")
    private String statut; // (ex: en traitement, expédiée, livrée)

    @Column(name = "date_commande")
    private LocalDateTime dateCommande;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }


}


