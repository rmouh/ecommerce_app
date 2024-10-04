package com.example.e_commerce.Controllers;

import com.example.e_commerce.Models.Review;
import com.example.e_commerce.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Ajouter un avis sur un produit
    @PostMapping("/add/{productId}")
    public ResponseEntity<Object> addReview(@PathVariable Long productId, @RequestBody ReviewDto reviewDto, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Utilisateur non authentifié");
        }

        String username = principal.getName();
        String result = reviewService.addReview(productId, username, reviewDto.getComment(), reviewDto.getRating());
        if (result.equals("Avis ajouté avec succès")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByProduct(@PathVariable Long productId) {
        try {
            List<Review> reviews = reviewService.getReviewsByProduct(productId);
            if (reviews.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<ReviewDto> reviewDtos = reviews.stream()
                    .map(review -> new ReviewDto(review.getComment(), review.getRating(), review.getUser().getUsername()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(reviewDtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    // Supprimer un avis par ID
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Object> deleteReview(@PathVariable Long reviewId) {
        String result = reviewService.deleteReview(reviewId);
        if (result.equals("Avis supprimé avec succès")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(400).body(result);
        }
    }
}
