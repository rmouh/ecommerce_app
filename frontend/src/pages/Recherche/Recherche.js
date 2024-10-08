import { useState } from 'react';
import { Link } from 'react-router-dom';
import Header from '../../components/header';
import Footer from '../../components/Footer';
import axios from 'axios';

function Recherche() {
    const [products, setProducts] = useState([]);
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(false);

    // Gérer la récupération des produits par collection
    const handleCollectionClick = async (collectionId) => {
        setLoading(true);
        try {
            // Appel de l'API backend pour obtenir les produits par collection ID
            const response = await axios.get(`http://localhost:8080/products/collection/${collectionId}`);
            if (response.data.length === 0) {
                setMessage('Aucun produit trouvé pour cette collection.');
                setProducts([]);
            } else {
                setMessage('');
                setProducts(response.data);
            }
        } catch (error) {
            setMessage('Erreur lors de la récupération des produits. Veuillez réessayer.');
            console.error('Erreur lors de la récupération des produits:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <Header />

            {/* Catégories Homme, Femme, Enfant */}
            <section className="categories-section">
                <div className="wrapper categories-container">
                    <div className="category-box" onClick={() => handleCollectionClick(1)}>
                        <h4>Homme</h4>
                    </div>
                    <div className="category-box" onClick={() => handleCollectionClick(2)}>
                        <h4>Femme</h4>
                    </div>
                    <div className="category-box" onClick={() => handleCollectionClick(3)}>
                        <h4>Enfant</h4>
                    </div>
                </div>
            </section>

            {/* Résultats de la recherche */}
            <div className="wrapper">
                {loading ? (
                    <div>Chargement...</div>
                ) : (
                    <>
                        {message && <div id="message-container" className="error-message">{message}</div>}
                        <div className="product-row">
                            {products.map((product, index) => (
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
                    </>
                )}
            </div>
        </div>
    );
}

export default Recherche;
