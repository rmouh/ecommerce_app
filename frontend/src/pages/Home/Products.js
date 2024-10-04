import { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

function Products() {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const response = await axios.get('http://localhost:8080/products/');
                setProducts(response.data);
                setMessage('');
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
                        {products.slice(0, 4).map((product, index) => (
                            <article
                                key={index}
                                className="product"
                                style={{
                                    backgroundImage: `url(${product.imageUrl})`,

                                }}
                            >
                                <div className="overlay">
                                    <h4>{product.name}</h4>
                                    <Link to={`/Article/${product.id}`} className="button-2">
                                        Voir les détails
                                    </Link>
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
