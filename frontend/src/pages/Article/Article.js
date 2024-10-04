import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../../components/header';
import Footer from '../../components/Footer';

function Article() {
    const { id } = useParams(); // Récupérer l'ID de l'article depuis l'URL
    const [product, setProduct] = useState(null);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const [quantity, setQuantity] = useState(1);
    const [successMessage, setSuccessMessage] = useState('');

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                // Appel de l'API backend pour obtenir le produit par ID
                const response = await axios.get(`http://localhost:8080/products/${id}`);
                setProduct(response.data);
                setMessage('');
            } catch (error) {
                setMessage('Erreur lors de la récupération de l\'article. Veuillez réessayer.');
                console.error('Erreur lors de la récupération de l\'article:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchProduct();
    }, [id]);

    // Gérer le changement de quantité
    const handleQuantityChange = (e) => {
        const value = Math.max(1, e.target.value); // Assurez-vous que la quantité est au moins 1
        setQuantity(value);
    };

    // Gérer l'ajout au panier
    const handleAddToCart = async () => {
        try {
            const response = await axios.post(`http://localhost:8080/cart/add/${id}/${quantity}`, {}, {
                withCredentials: true, // Inclure les cookies d'authentification si nécessaire
            });
            setSuccessMessage(response.data); // Utilisez le message renvoyé par le backend pour informer l'utilisateur
            setMessage('');
        } catch (error) {
            setMessage('Erreur lors de l\'ajout au panier. Veuillez réessayer.');
            console.error('Erreur lors de l\'ajout au panier:', error);
        }
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
            <div className="article-details wrapper">
                {product ? (
                    <div className="product-container">
                        <img
                            className="product-image"
                            src={product.imageUrl || 'images/default.jpg'}
                            alt={product.name}
                            style={{ width: '400px', height: '400px', borderRadius: '10px' }}
                        />
                        <div className="product-info">
                            <h2>{product.name}</h2>
                            <p><strong>Description:</strong> {product.description}</p>
                            <p><strong>Prix:</strong> {product.price} €</p>
                            {product.collection && (
                                <p><strong>Collection:</strong> {product.collection.name}</p>
                            )}
                            <p><strong>Quantité en stock:</strong> {product.stockQuantity}</p>

                            {/* Ajouter la quantité souhaitée */}
                            <div className="quantity-container">
                                <label htmlFor="quantity"><strong>Quantité souhaitée :</strong></label>
                                <input
                                    type="number"
                                    id="quantity"
                                    name="quantity"
                                    value={quantity}
                                    onChange={handleQuantityChange}
                                    min="1"
                                    max={product.stockQuantity}
                                    style={{ margin: '10px', padding: '5px', width: '60px' }}
                                />
                            </div>

                            {/* Bouton pour ajouter au panier */}
                            <button onClick={handleAddToCart} className="button-add-cart">
                                Ajouter au panier
                            </button>

                            {/* Message de succès ou d'erreur */}
                            {successMessage && <div className="success-message">{successMessage}</div>}
                            {message && <div className="error-message">{message}</div>}
                        </div>
                    </div>
                ) : (
                    <p>Produit non trouvé</p>
                )}
            </div>
        </div>
    );
}

export default Article;
