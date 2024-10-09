// src/pages/Checkout/Checkout.js

import { useNavigate } from 'react-router-dom'; // Modifié ici
import axios from 'axios';

const Checkout = () => {
    const navigate = useNavigate(); // Remplacez history par navigate

    const handleProceedToPayment = async () => {
        const totalAmount = 20; // Remplacez par le montant réel
        const productName = 'Nom du produit';
        const quantity = 1;
        const utilisateurId = 1; // Remplacez par l'ID de l'utilisateur connecté

        try {
            // Créer la commande
            const response = await axios.post(`/api/cmd/create/${utilisateurId}`);
            const createdCommande = response.data;

            // Rediriger vers la page de paiement avec les informations de la commande
            navigate('/payment', { // Modifié ici
                state: {
                    amount: totalAmount,
                    productName,
                    quantity,
                    commandeId: createdCommande.id, // Inclure l'ID de la commande créée
                },
            });
        } catch (error) {
            console.error('Erreur lors de la création de la commande:', error);
        }
    };

    return (
        <div>
            <h1>Vérifiez votre commande</h1>
            <button onClick={handleProceedToPayment}>Payer maintenant</button>
        </div>
    );
};

export default Checkout;
