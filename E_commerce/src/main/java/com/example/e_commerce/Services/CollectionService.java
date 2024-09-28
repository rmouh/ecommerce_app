package com.example.e_commerce.Services;

import com.example.e_commerce.Models.Collection;
import com.example.e_commerce.Repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    // Méthode pour créer une nouvelle collection
    public Collection createCollection(Collection collection) {
        collection.setNbProducts(0L); // Définit le nombre de produits à 0 par défaut
        return collectionRepository.save(collection);
    }

    // Méthode pour obtenir une collection par ID
    public Collection getCollectionById(Long id) {
        Optional<Collection> collection = collectionRepository.findById(id);
        return collection.orElse(null); // Retourne null si la collection n'existe pas
    }

    // Méthode pour obtenir toutes les collections
    public Iterable<Collection> getAllCollections() {
        return collectionRepository.findAll();
    }
}

