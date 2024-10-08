package com.example.e_commerce.Controllers;

import com.example.e_commerce.Models.Collection;
import com.example.e_commerce.Repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/collections")
@CrossOrigin(origins = "http://localhost:3000") // Permettre les requêtes CORS pour votre application front-end
public class CollectionController {

    @Autowired
    private CollectionRepository collectionRepository;

    // GET: Récupérer toutes les collections
    @GetMapping("/")
    public ResponseEntity<List<Collection>> getAllCollections() {
        try {
            List<Collection> collections = collectionRepository.findAll();
            if (collections.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    // GET: Récupérer une collection par ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCollectionById(@PathVariable Long id) {
        try {
            Optional<Collection> collection = collectionRepository.findById(id);
            if (collection.isPresent()) {
                return ResponseEntity.ok(collection.get());
            } else {
                return ResponseEntity.status(404).body("Collection non trouvée");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur interne du serveur");
        }
    }

    // POST: Créer une nouvelle collection
    @PostMapping("/")
    public ResponseEntity<Object> createCollection(@RequestBody Collection collection) {
        try {
            Collection newCollection = collectionRepository.save(collection);
            return ResponseEntity.ok(newCollection);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la création de la collection");
        }
    }

    // DELETE: Supprimer une collection par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCollection(@PathVariable Long id) {
        try {
            Optional<Collection> collection = collectionRepository.findById(id);
            if (collection.isPresent()) {
                collectionRepository.deleteById(id);
                return ResponseEntity.ok("Collection supprimée avec succès");
            } else {
                return ResponseEntity.status(404).body("Collection non trouvée");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la suppression de la collection");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateCollection(@PathVariable Long id, @RequestBody Collection collectionDetails) {
        try {
            Optional<Collection> collectionOptional = collectionRepository.findById(id);
            if (!collectionOptional.isPresent()) {
                return ResponseEntity.status(404).body("Collection non trouvée");
            }

            Collection collection = collectionOptional.get();

            // Mettre à jour le nom si fourni
            if (collectionDetails.getName() != null && !collectionDetails.getName().isEmpty()) {
                collection.setName(collectionDetails.getName());
            }

            // Mettre à jour la description si fournie
            if (collectionDetails.getDescription() != null && !collectionDetails.getDescription().isEmpty()) {
                collection.setDescription(collectionDetails.getDescription());
            }

            // Mettre à jour le stock si fourni et non négatif
            if (collectionDetails.getNbProducts() != null && collectionDetails.getNbProducts() >= 0) {
                collection.setNbProducts(collectionDetails.getNbProducts());
            }

            Collection updatedCollection = collectionRepository.save(collection);
            return ResponseEntity.ok(updatedCollection);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la mise à jour de la collection");
        }
    }

}
