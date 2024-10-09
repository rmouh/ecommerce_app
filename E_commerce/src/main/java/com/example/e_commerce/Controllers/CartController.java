package com.example.e_commerce.Controllers;

import com.example.e_commerce.Services.OrderService;
import com.example.e_commerce.Models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.e_commerce.Models.UserApp;
import com.example.e_commerce.Repositories.UserRepository;
import java.util.Optional;
import java.security.Principal;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository; // Inject the UserRepository

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

    // Récupérer le panier de l'utilisateur connecté
    @GetMapping("/my-cart")
    public ResponseEntity<Object> getUserCart(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");
        }

        String username = principal.getName();
        UserApp userApp = userRepository.findByUsername(username); // Use the instance of userRepository

        if (userApp == null) {
            return ResponseEntity.status(404).body("Utilisateur non trouvé.");
        }

        // Récupérer la commande en cours (le panier)
        Optional<Order> cart = orderService.getCartByUserId(userApp.getId());

        if (!cart.isPresent()) {
            return ResponseEntity.status(404).body("Aucun panier trouvé.");
        }

        return ResponseEntity.ok(cart.get());
    }

}
