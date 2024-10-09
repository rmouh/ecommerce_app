package com.example.e_commerce.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import lombok.Data;
@Entity
@Table(name = "payment")

@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPayment")
    private Long id;

    @Column(name="user_email")
    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private UserApp user;


    @OneToOne
    @JoinColumn(name = "idOrder", nullable = false)
    private Order order;

    @Column(name = "amount")
    private double amount;


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


    public UserApp getUser() {
        return user;
    }

    public void setUser(UserApp user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



}
