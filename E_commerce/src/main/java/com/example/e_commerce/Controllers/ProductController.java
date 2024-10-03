package com.example.e_commerce.Controllers;

import com.example.e_commerce.Models.Collection;
import com.example.e_commerce.Models.Product;
import com.example.e_commerce.Repositories.CollectionRepository;
import com.example.e_commerce.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:3000") // Permettre les requêtes CORS pour l'application front-end
@RestController
@RequestMapping("/products")

public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    // GET: Récupérer tous les produits
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // GET: Récupérer un produit par ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                return ResponseEntity.ok(product.get());
            } else {
                return ResponseEntity.status(404).body("Produit non trouvé");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur");
        }
    }

    // POST: Créer un nouveau produit
    @PostMapping("/")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        try {
            if (product.getCollection() != null) {
                Optional<Collection> collectionOptional = collectionRepository.findById(product.getCollection().getId());
                if (collectionOptional.isPresent()) {
                    product.setCollection(collectionOptional.get());
                } else {
                    return ResponseEntity.status(404).body("Collection non trouvée");
                }
            }

            Product newProduct = productRepository.save(product);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la création du produit");
        }
    }

    // PUT: Mettre à jour un produit par ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        try {
            Optional<Product> productOptional = productRepository.findById(id);
            if (!productOptional.isPresent()) {
                return ResponseEntity.status(404).body("Produit non trouvé");
            }

            Product product = productOptional.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setImageUrl(productDetails.getImageUrl());

            if (productDetails.getCollection() != null) {
                Optional<Collection> collectionOptional = collectionRepository.findById(productDetails.getCollection().getId());
                if (collectionOptional.isPresent()) {
                    product.setCollection(collectionOptional.get());
                } else {
                    return ResponseEntity.status(404).body("Collection non trouvée");
                }
            }

            Product updatedProduct = productRepository.save(product);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la mise à jour du produit");
        }
    }

    // DELETE: Supprimer un produit par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                productRepository.deleteById(id);
                return ResponseEntity.ok("Produit supprimé avec succès");
            } else {
                return ResponseEntity.status(404).body("Produit non trouvé");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la suppression du produit");
        }
    }
}
