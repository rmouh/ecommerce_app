package com.example.e_commerce.Models;

import com.example.e_commerce.Models.Commande;
import com.example.e_commerce.Models.Order;
import jakarta.persistence.*;


@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private String status; // Exemples : "Pending", "Shipped", "Delivered"

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Commande order; // Lien avec la commande

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Commande getOrder() {
        return order;
    }

    public void setOrder(Commande order) {
        this.order = order;
    }
}