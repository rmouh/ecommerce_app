package com.example.e_commerce.Controllers;
public class ReviewDto {
    private String comment;
    private int rating;
    private String username; // Nom de l'utilisateur qui a laissé l'avis

    // Constructeur
    public ReviewDto(String comment, int rating, String username) {
        this.comment = comment;
        this.rating = rating;
        this.username = username;
    }

    // Getters et Setters
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
