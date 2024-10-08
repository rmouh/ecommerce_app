import { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Importer useNavigate
import Header from '../../components/header';
import Footer from '../../components/Footer';

function Panier() {
    const [cartItems, setCartItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const navigate = useNavigate(); // Initialiser useNavigate

    useEffect(() => {
        const fetchCartItems = async () => {
            try {
                const token = localStorage.getItem('token'); // Récupérer le token de l'utilisateur connecté
                if (!token) {
                    setMessage("Veuillez vous connecter pour voir votre panier.");
                    return;
                }

                // Appel à l'API pour obtenir les articles du panier
                const response = await axios.get('http://localhost:8080/cart/my-cart', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });

                setCartItems(response.data.orderItems); // Enregistrer les articles dans l'état
                setMessage('');
            } catch (error) {
                setMessage("Erreur lors de la récupération des articles du panier.");
                console.error('Erreur:', error.response?.data || error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchCartItems();
    }, []);

    // Fonction pour valider le panier et passer au paiement
    const handleValidation = () => {
        navigate('/commande'); // Rediriger vers la page commande
    };

    if (loading) {
        return (
            <div>
                <Header />
                <div>Chargement...</div>
                <Footer />
            </div>
        );
    }

    if (message) {
        return (
            <div>
                <Header />
                <div className="error-message">{message}</div>
                <Footer />
            </div>
        );
    }

    return (
        <div>
            <Header />
            <div className="panier-container wrapper">
                <h2>Mon Panier</h2>
                {cartItems.length > 0 ? (
                    <div>
                        {cartItems.map((item, index) => (
                            <div key={index} className="cart-item">
                                <img
                                    src={item.product.imageUrl || 'images/default.jpg'}
                                    alt={item.product.name}
                                    style={{width: '100px', height: '100px'}}
                                />
                                <div className="item-info">
                                    <h3>{item.product.name}</h3>
                                    <p>{item.product.description}</p>
                                    <p><strong>Prix :</strong> {item.price} €</p>
                                    <p><strong>Quantité :</strong> {item.quantity}</p>
                                </div>
                            </div>
                        ))}

                        {/* Bouton pour valider le panier et rediriger vers la page commande */}
                        
                        <div className="cart-footer">
                            <button className="checkout-button" onClick={handleValidation}>
                                Valider le panier et passer au paiement
                            </button>
                        </div>
                    </div>
                ) : (
                    <p>Votre panier est vide.</p>
                )}
            </div>
            <Footer/>
        </div>
    );
}

export default Panier;
