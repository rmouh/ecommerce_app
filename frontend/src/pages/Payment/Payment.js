import React, { useState } from 'react';

const PaymentPage = () => {
    const [amount, setAmount] = useState(1000); // Montant par défaut (en cents)
    const [productName, setProductName] = useState("Produit Exemple");
    const [quantity, setQuantity] = useState(1);

    const handleCheckout = async () => {
        const response = await fetch('http://localhost:8080/api/payments/create-session', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                amount: amount * quantity, // Montant en cents, multiplié par la quantité
                productName: productName,
                quantity: quantity,
            }),
        });

        if (response.ok) {
            const data = await response.json();
            if (data.sessionId) {
                // Redirige vers Stripe pour le paiement
                window.location.href = `https://checkout.stripe.com/pay/${data.sessionId}`;
            } else {
                alert("Erreur lors de la création de la session de paiement");
            }
        } else {
            const errorText = await response.text();
            alert(`Erreur : ${response.status} - ${errorText}`);
        }
    };

    return (
        <div>
            <h1>Paiement</h1>
            <div>
                <label>Nom du produit:</label>
                <input
                    type="text"
                    value={productName}
                    onChange={(e) => setProductName(e.target.value)}
                />
            </div>
            <div>
                <label>Montant (en cents):</label>
                <input
                    type="number"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                />
            </div>
            <div>
                <label>Quantité:</label>
                <input
                    type="number"
                    value={quantity}
                    onChange={(e) => setQuantity(e.target.value)}
                />
            </div>
            <button onClick={handleCheckout}>Procéder au paiement</button>
        </div>
    );
};

export default PaymentPage;
