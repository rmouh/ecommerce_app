package com.example.e_commerce.Controllers;

import com.example.e_commerce.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private OrderService orderService;

    // Ajouter un produit au panier de l'utilisateur connecté
    @PostMapping("/add/{productId}/{quantity}")
    public ResponseEntity<Object> addProductToCart(@PathVariable Long productId, @PathVariable int quantity,
                                                   Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");
        }

        String username = principal.getName();
        String result = orderService.addProductToCart(productId, quantity, username);
        if (result.equals("Produit ajouté au panier avec succès")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

    // Supprimer une quantité spécifique d'un produit du panier de l'utilisateur connecté
    @DeleteMapping("/remove/{productId}/{quantity}")
    public ResponseEntity<Object> removeProductFromCart(@PathVariable Long productId, @PathVariable int quantity, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");
        }

        String username = principal.getName();
        String result = orderService.removeProductFromCart(productId, quantity, username);
        if (result.equals("Quantite de produit retiré du panier avec succès")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }
    // Mettre à jour la quantité d'un produit dans le panier de l'utilisateur connecté
    @PutMapping("/update/{productId}/{quantity}")
    public ResponseEntity<Object> updateProductQuantityInCart(@PathVariable Long productId, @PathVariable int quantity, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");
        }

        String username = principal.getName();
        String result = orderService.updateProductQuantityInCart(productId, quantity, username);
        if (result.equals("Quantité mise à jour avec succès")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

    // Supprimer tous les produits du panier de l'utilisateur connecté
    @DeleteMapping("/removeAll")
    public ResponseEntity<Object> removeAllProductsFromCart(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");
        }

        String username = principal.getName();
        String result = orderService.removeAllProductsFromCart(username);

        if (result.equals("Tous les produits ont été retirés du panier avec succès")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

    // Supprimer un produit spécifique du panier de l'utilisateur connecté
    @DeleteMapping("/removeProduct/{productId}")
    public ResponseEntity<Object> removeOneProductFromCart(
            @PathVariable Long productId,
            Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");
        }

        String username = principal.getName();
        String result = orderService.removeOneProductFromCart(username, productId);

        if (result.equals("Produit retiré du panier avec succès")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

}
