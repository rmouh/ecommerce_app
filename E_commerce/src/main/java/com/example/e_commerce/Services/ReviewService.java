package com.example.e_commerce.Services;

import com.example.e_commerce.Models.Review;
import com.example.e_commerce.Models.Product;
import com.example.e_commerce.Models.UserApp;
import com.example.e_commerce.Repositories.ProductRepository;
import com.example.e_commerce.Repositories.ReviewRepository;
import com.example.e_commerce.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Ajouter un avis sur un produit
    public String addReview(Long productId, String username, String comment, int rating) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            return "Produit non trouvé";
        }

        Product product = optionalProduct.get();
        UserApp user = userRepository.findByUsername(username);
        if (user == null) {
            return "Utilisateur non trouvé";
        }

        Review review = new Review(user, product, comment, rating, new Date());
        reviewRepository.save(review);

        return "Avis ajouté avec succès";
    }

    // Obtenir tous les avis pour un produit donné
    public List<Review> getReviewsByProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            throw new IllegalArgumentException("Produit non trouvé");
        }

        return reviewRepository.findByProduct(optionalProduct.get());
    }

    // Supprimer un avis par ID
    public String deleteReview(Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (!optionalReview.isPresent()) {
            return "Avis non trouvé";
        }

        reviewRepository.delete(optionalReview.get());
        return "Avis supprimé avec succès";
    }
}
