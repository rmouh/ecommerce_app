import { useState } from 'react';
import axios from 'axios';


function Commande() {
    const [address, setAddress] = useState('');
    const [phone, setPhone] = useState('');
    const [cardNumber, setCardNumber] = useState('');
    const [cardName, setCardName] = useState('');
    const [expiryDate, setExpiryDate] = useState('');
    const [cvv, setCvv] = useState('');
    const [message, setMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        const token = localStorage.getItem('token'); // Récupérer le token de l'utilisateur connecté
        if (!token) {
            setMessage('Veuillez vous connecter pour effectuer une commande.');
            return;
        }

        const orderDetails = {
            address,
            phone,
            cardNumber,
            cardName,
            expiryDate,
            cvv
        };

        try {
            // Appel à l'API pour soumettre les informations de commande
            const response = await axios.post('http://localhost:8080/order/submit', orderDetails, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            setMessage('Commande validée avec succès.');
        } catch (error) {
            console.error('Erreur lors de la validation de la commande:', error.response?.data || error.message);
            setMessage('Erreur lors de la validation de la commande. Veuillez réessayer.');
        }
    };

    return (
        <div>

            <div className="commande-container wrapper">
                <h2>Valider la commande et passer au paiement</h2>
                <form onSubmit={handleSubmit} className="commande-form">
                    <div className="form-group">
                        <label htmlFor="address">Adresse de livraison :</label>
                        <input
                            type="text"
                            id="address"
                            value={address}
                            onChange={(e) => setAddress(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="phone">Numéro de téléphone :</label>
                        <input
                            type="tel"
                            id="phone"
                            value={phone}
                            onChange={(e) => setPhone(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="cardNumber">Numéro de carte bancaire :</label>
                        <input
                            type="text"
                            id="cardNumber"
                            value={cardNumber}
                            onChange={(e) => setCardNumber(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="cardName">Nom sur la carte :</label>
                        <input
                            type="text"
                            id="cardName"
                            value={cardName}
                            onChange={(e) => setCardName(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="expiryDate">Date d'expiration :</label>
                        <input
                            type="month"
                            id="expiryDate"
                            value={expiryDate}
                            onChange={(e) => setExpiryDate(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="cvv">Code CVV :</label>
                        <input
                            type="password"
                            id="cvv"
                            value={cvv}
                            onChange={(e) => setCvv(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="button-validate">Valider la commande</button>
                </form>
                {message && <div className="message">{message}</div>}
            </div>

        </div>
    );
}

export default Commande;
