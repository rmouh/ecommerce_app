import React from 'react';
import { useNavigate } from 'react-router-dom';

const ProductSelection = () => {
    const navigate = useNavigate();

    const handleProductSelection = (product) => {
        // Lorsque l'utilisateur sélectionne un produit, rediriger vers la page de paiement avec les détails du produit
        const { amount, productName, quantity } = product;
        navigate('/payment', { state: { amount, productName, quantity } });
    };

    const products = [
        { id: 1, name: "Produit A", amount: 2000, quantity: 1 }, // 20,00 €
        { id: 2, name: "Produit B", amount: 1500, quantity: 1 }, // 15,00 €
        { id: 3, name: "Produit C", amount: 1000, quantity: 1 }, // 10,00 €
    ];

    return (
        <div>
            <h2>Sélectionnez un Produit</h2>
            <ul>
                {products.map(product => (
                    <li key={product.id}>
                        <span>{product.name} - {(product.amount / 100).toFixed(2)} €</span>
                        <button onClick={() => handleProductSelection(product)}>Acheter</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ProductSelection;
