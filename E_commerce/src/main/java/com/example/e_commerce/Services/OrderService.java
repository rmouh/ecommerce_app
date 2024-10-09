package com.example.e_commerce.Services;
import org.springframework.transaction.annotation.Transactional;
import com.example.e_commerce.Models.Order;
import com.example.e_commerce.Models.OrderItem;
import com.example.e_commerce.Models.Product;
import com.example.e_commerce.Models.UserApp;
import com.example.e_commerce.Repositories.OrderItemRepository;
import com.example.e_commerce.Repositories.OrderRepository;
import com.example.e_commerce.Repositories.ProductRepository;
import com.example.e_commerce.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.List;
@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Méthode pour ajouter un produit au panier
    public String addProductToCart(Long productId, int quantity, String username) {
        // Vérifier si le produit existe
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            return "Produit non trouvé";
        }

        Product product = optionalProduct.get();

        // Vérifier si la quantité est suffisante
        if (product.getStockQuantity() < quantity) {
            return "Quantité demandée non disponible";
        }

        // Récupérer l'utilisateur connecté
        UserApp userApp = userRepository.findByUsername(username);
        if (userApp == null) {
            return "Utilisateur non trouvé";
        }

        // Vérifier s'il y a déjà une commande en cours pour cet utilisateur
        Order order = orderRepository.findByUserAndStatus(userApp, "PENDING")
                .orElse(new Order(userApp, new Date(), "PENDING"));

        // Sauvegarder la commande si elle est nouvelle
        if (order.getId() == null) {
            order = orderRepository.save(order);
        }

        // Initialiser le Set si nécessaire
        if (order.getOrderItems() == null) {
            order.setOrderItems(new HashSet<>());
        }

        // Vérifier si le produit est déjà dans le panier
        Optional<OrderItem> existingOrderItem = order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getProduct().getId().equals(productId))
                .findFirst();

        if (existingOrderItem.isPresent()) {
            // Si le produit est déjà dans le panier, mettre à jour la quantité
            OrderItem orderItem = existingOrderItem.get();
            orderItem.setQuantity(orderItem.getQuantity() + quantity);
            orderItemRepository.save(orderItem);
        } else {
            // Sinon, ajouter un nouvel élément de commande
            OrderItem orderItem = new OrderItem(order, product, quantity, product.getPrice());
            order.getOrderItems().add(orderItem);
            orderItemRepository.save(orderItem);
        }

        // Mettre à jour la quantité du produit
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        return "Produit ajouté au panier avec succès";
    }

    // Méthode pour supprimer un produit du panier
    public String removeProductFromCart(Long productId, int quantity, String username) {
        // Récupérer l'utilisateur connecté
        UserApp userApp = userRepository.findByUsername(username);
        if (userApp == null) {
            return "Utilisateur non trouvé";
        }

        // Récupérer la commande en cours
        Optional<Order> optionalOrder = orderRepository.findByUserAndStatus(userApp, "PENDING");
        if (!optionalOrder.isPresent()) {
            return "Aucune commande en cours trouvée";
        }

        Order order = optionalOrder.get();

        // Vérifier si le produit est dans le panier
        Optional<OrderItem> optionalOrderItem = order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getProduct().getId().equals(productId))
                .findFirst();

        if (!optionalOrderItem.isPresent()) {
            return "Produit non trouvé dans le panier";
        }

        OrderItem orderItem = optionalOrderItem.get();

        // Vérifier la quantité à supprimer
        if (quantity >= orderItem.getQuantity()) {
            // Supprimer l'élément de commande si la quantité est égale ou supérieure
            Product product = orderItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
            productRepository.save(product);

            // Supprimer l'élément de commande du panier
            order.getOrderItems().remove(orderItem);
            orderItemRepository.delete(orderItem);
        } else {
            // Mettre à jour la quantité si elle est inférieure à la quantité de l'élément de commande
            orderItem.setQuantity(orderItem.getQuantity() - quantity);

            // Mettre à jour la quantité du produit en stock
            Product product = orderItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() + quantity);
            productRepository.save(product);

            // Sauvegarder les modifications de l'élément de commande
            orderItemRepository.save(orderItem);
        }

        return "Produit retiré du panier avec succès";
    }

    // Méthode pour trouver toutes les commandes d'un utilisateur par ID
    public List<Order> findOrdersByUserId(Long userId) {
        Optional<UserApp> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return orderRepository.findByUser(user.get());
        } else {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }
    }
    public String updateProductQuantityInCart(Long productId, int quantity, String username) {
        // Vérifier si le produit existe
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent()) {
            return "Produit non trouvé";
        }

        Product product = optionalProduct.get();

        // Vérifier si la quantité demandée est valide
        if (quantity < 0) {
            return "Quantité invalide";
        }

        // Récupérer l'utilisateur connecté
        UserApp userApp = userRepository.findByUsername(username);
        if (userApp == null) {
            return "Utilisateur non trouvé";
        }

        // Récupérer la commande en cours
        Order order = orderRepository.findByUserAndStatus(userApp, "PENDING")
                .orElse(new Order(userApp, new Date(), "PENDING"));

        // Sauvegarder la commande si elle est nouvelle
        if (order.getId() == null) {
            order = orderRepository.save(order);
        }

        // Initialiser le Set si nécessaire
        if (order.getOrderItems() == null) {
            order.setOrderItems(new HashSet<>());
        }

        // Vérifier si le produit est déjà dans le panier
        Optional<OrderItem> existingOrderItem = order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getProduct().getId().equals(productId))
                .findFirst();

        if (existingOrderItem.isPresent()) {
            OrderItem orderItem = existingOrderItem.get();

            // Si la quantité est 0, supprimer l'élément de commande
            if (quantity == 0) {
                // Mettre à jour la quantité du produit en stock
                product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
                productRepository.save(product);

                // Supprimer l'élément de commande du panier
                order.getOrderItems().remove(orderItem);
                orderItemRepository.delete(orderItem);

                return "Produit retiré du panier avec succès";
            }

            // Sinon, mettre à jour la quantité
            int currentQuantity = orderItem.getQuantity();
            int quantityDifference = quantity - currentQuantity;

            // Vérifier la disponibilité du stock
            if (quantityDifference > 0 && product.getStockQuantity() < quantityDifference) {
                return "Quantité demandée non disponible";
            }

            // Mettre à jour la quantité de l'article dans le panier
            orderItem.setQuantity(quantity);
            orderItemRepository.save(orderItem);

            // Mettre à jour la quantité du produit en stock
            product.setStockQuantity(product.getStockQuantity() - quantityDifference);
            productRepository.save(product);

        } else {
            // Si le produit n'est pas dans le panier, l'ajouter
            if (quantity == 0) {
                return "La quantité est 0, aucun changement n'a été effectué.";
            }

            if (product.getStockQuantity() < quantity) {
                return "Quantité demandée non disponible";
            }

            OrderItem orderItem = new OrderItem(order, product, quantity, product.getPrice());
            order.getOrderItems().add(orderItem);
            orderItemRepository.save(orderItem);

            // Mettre à jour la quantité du produit
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepository.save(product);
        }

        return "Quantité mise à jour avec succès";
    }
    @Transactional
    public Optional<Order> getCartByUserId(Long userId) {
        Optional<UserApp> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return orderRepository.findByUserAndStatus(user.get(), "PENDING");
        } else {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }
    }

    // Méthode pour supprimer tous les produits du panier de l'utilisateur connecté
    public String removeAllProductsFromCart(String username) {
        // Récupérer l'utilisateur connecté
        UserApp userApp = userRepository.findByUsername(username);
        if (userApp == null) {
            return "Utilisateur non trouvé";
        }

        // Récupérer la commande en cours
        Optional<Order> optionalOrder = orderRepository.findByUserAndStatus(userApp, "PENDING");
        if (!optionalOrder.isPresent()) {
            return "Aucune commande en cours trouvée";
        }

        Order order = optionalOrder.get();

        // Parcourir tous les éléments de commande pour mettre à jour les quantités de stock
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());
            productRepository.save(product);
        }

        // Supprimer tous les éléments de commande
        orderItemRepository.deleteAll(order.getOrderItems());
        order.getOrderItems().clear();
        orderRepository.save(order); // Sauvegarder l'état de la commande

        return "Tous les produits ont été retirés du panier avec succès";
    }
    // Méthode pour supprimer un produit spécifique du panier de l'utilisateur
    public String removeOneProductFromCart(String username, Long productId) {
        // Récupérer l'utilisateur connecté
        UserApp userApp = userRepository.findByUsername(username);
        if (userApp == null) {
            return "Utilisateur non trouvé";
        }

        // Récupérer la commande en cours
        Optional<Order> optionalOrder = orderRepository.findByUserAndStatus(userApp, "PENDING");
        if (!optionalOrder.isPresent()) {
            return "Aucune commande en cours trouvée";
        }

        Order order = optionalOrder.get();

        // Vérifier si le produit est dans le panier
        Optional<OrderItem> optionalOrderItem = order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getProduct().getId().equals(productId))
                .findFirst();

        if (!optionalOrderItem.isPresent()) {
            return "Produit non trouvé dans le panier";
        }

        // Supprimer l'élément de commande du panier
        OrderItem orderItem = optionalOrderItem.get();
        Product product = orderItem.getProduct();

        // Récupérer la quantité à supprimer
        int quantityToRemove = orderItem.getQuantity();

        // Mettre à jour la quantité du produit dans le stock
        product.setStockQuantity(product.getStockQuantity() + quantityToRemove);
        productRepository.save(product);

        // Supprimer l'élément de commande du panier
        order.getOrderItems().remove(orderItem);
        orderItemRepository.delete(orderItem);

        return "Produit retiré du panier avec succès";
    }




}
