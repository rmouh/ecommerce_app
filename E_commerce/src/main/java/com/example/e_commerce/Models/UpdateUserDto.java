package com.example.e_commerce.Models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UpdateUserDto {

    @NotBlank(message = "Le prénom ne peut pas être vide")
    private String firstName;

    @NotBlank(message = "Le nom ne peut pas être vide")
    private String lastName;

    @Email(message = "Adresse e-mail invalide")
    @NotBlank(message = "L'email ne peut pas être vide")
    private String email;

    private String address;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;

    // Getters et setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
