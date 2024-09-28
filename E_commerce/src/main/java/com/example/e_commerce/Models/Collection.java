package com.example.e_commerce.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "collections")
public class Collection {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long nbProducts = 0L;  // Valeur par défau
    // Relation OneToMany avec Product
    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    public Collection() {

    }


    public Collection(Long id, String name, String description, Long nbProducts, List<Product> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.nbProducts = nbProducts;
        this.products = products;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getNbProducts() {
        return nbProducts;
    }

    public void setNbProducts(Long nbProducts) {
        this.nbProducts = nbProducts;
    }
}
