import { useState, useEffect } from 'react';
import axios from 'axios';

function Products() {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');

    // Charger les produits depuis l'API backend
    useEffect(() => {
        const fetchProducts = async () => {
            try {
                // Appel de l'API backend pour obtenir les produits
                const response = await axios.get('http://localhost:8080/products/');
                setProducts(response.data);
                setMessage(''); // Remettre le message à zéro en cas de succès
            } catch (error) {
                setMessage('Erreur lors de la récupération des produits. Veuillez réessayer.');
                console.error('Erreur lors de la récupération des produits:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, []);

    return (
        <section id="possibilities">
            <div className="wrapper">
                <h3>Produits populaires</h3>
                {loading ? (
                    <div>Chargement...</div>
                ) : (
                    <div className="product-row">
                        {message && <div className="error-message">{message}</div>}
                        {products.map((product, index) => (
                            <article
                                key={index}
                                className="product"
                                style={{
                                    backgroundImage: `url(${product.imageUrl || '../public/images/comm5.jpg'})`
                                }}
                            >
                                <div className="overlay">
                                    <h4>{product.name}</h4>
                                    <a href={`produit${index + 1}.html`} className="button-2">
                                        Voir les détails
                                    </a>
                                </div>
                            </article>
                        ))}
                    </div>
                )}
            </div>
        </section>
    );
}

export default Products;
