package com.example.e_commerce.Controllers;


import com.example.e_commerce.Models.Collection;
import com.example.e_commerce.Services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    // Endpoint pour créer une nouvelle collection
    @PostMapping("add")
    public ResponseEntity<Collection> createCollection(@RequestBody Collection collection) {
        Collection newCollection = collectionService.createCollection(collection);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCollection);
    }

    // Endpoint pour obtenir une collection par ID
    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable Long id) {
        Collection collection = collectionService.getCollectionById(id);
        if (collection != null) {
            return ResponseEntity.ok(collection);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Endpoint pour obtenir toutes les collections
    @GetMapping("getAll")
    public ResponseEntity<Iterable<Collection>> getAllCollections() {
        Iterable<Collection> collections = collectionService.getAllCollections();
        return ResponseEntity.ok(collections);
    }
}
