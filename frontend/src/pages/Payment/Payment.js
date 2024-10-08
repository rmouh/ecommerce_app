import React, { useState } from 'react';
import axios from 'axios';

function PaymentPage() {
    const [productName, setProductName] = useState('Product A');
    const [price, setPrice] = useState(5000); // Exemple : 50.00 EUR en centimes
    const [currency, setCurrency] = useState('eur');

    const handlePayment = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/stripe/create-checkout-session', {
                price: price,
                productName: productName,
                currency: currency
            });

            const sessionId = response.data.sessionId;

            // Redirection vers Stripe Checkout avec l'ID de session
            window.location.href = `https://checkout.stripe.com/pay/${sessionId}`;
        } catch (error) {
            console.error('Erreur lors de la création de la session Stripe:', error);
        }
    };

    return (
        <div>
            <h2>Payment for {productName}</h2>
            <p>Price: {price / 100} {currency.toUpperCase()}</p>
            <button onClick={handlePayment}>Proceed to Payment</button>
        </div>
    );
}

export default PaymentPage;
