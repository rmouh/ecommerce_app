package com.example.projectcode.Models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    //@Column(nullable = false)
    //private int stockQuantity;

    @Column
    private String imageUrl;

    // Getters and Setters

    // Default constructor
    public Product() {}

    // Constructor with fields
    public Product(String name, String description, BigDecimal price, int stockQuantity, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        //this.stockQuantity = stockQuantity;
        this.imageUrl = imageUrl;
    }

    // toString, equals, and hashCode methods (optional)


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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /*public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }*/

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

