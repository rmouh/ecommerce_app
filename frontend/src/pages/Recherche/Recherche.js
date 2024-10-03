import { useState, useEffect } from 'react';
import Header from '../../components/header';
import Footer from '../../components/Footer';
import axios from 'axios';

function Recherche() {
    const [critere, setCritere] = useState('');
    const [products, setProducts] = useState([]);
    const [message, setMessage] = useState('');
    const [loading, setLoading] = useState(true);

    // Charger tous les produits dès le chargement de la page
    useEffect(() => {
        const fetchAllProducts = async () => {
            try {
                // Appel de l'API backend pour obtenir tous les produits
                const response = await axios.get('http://localhost:8080/products/');
                setProducts(response.data);
                setMessage(''); // Remet le message à zéro s'il n'y a pas d'erreur
            } catch (error) {
                setMessage('Erreur lors de la récupération des produits. Veuillez réessayer.');
                console.error('Erreur lors de la récupération des produits:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchAllProducts();
    }, []);

    // Gérer le changement de critère de recherche
    const handleChange = (e) => {
        setCritere(e.target.value);
    };

    // Gérer la soumission du formulaire de recherche
    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            // Appel de l'API backend pour effectuer la recherche
            const response = await axios.post('http://localhost:8080/Recherche', {
                critere: critere,
            });

            // Si la recherche réussit
            if (response.data.length === 0) {
                setMessage('Aucun résultat trouvé.');
                setProducts([]);
            } else {
                setMessage('');
                setProducts(response.data);
            }
        } catch (error) {
            setMessage('Erreur lors de la recherche. Veuillez réessayer.');
            console.error('Erreur lors de la recherche:', error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <Header />

            {/* Formulaire de recherche */}
            <section id="main-image">
                <div className="wrapper">
                    <div className="search-container">
                        <form onSubmit={handleSubmit} className="search-form">
                            <label htmlFor="critere">Recherche</label>
                            <input
                                type="text"
                                id="critere"
                                name="critere"
                                value={critere}
                                onChange={handleChange}
                                placeholder="Saisissez le nom du produit ou la description"
                                required
                            />
                            <button type="submit">Rechercher</button>
                        </form>
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
                        <div className="book-row">
                            {products.map((product, index) => (
                                <div className="book" key={index}>
                                    <img src={product.imageUrl || 'images/default.jpg'} alt="Image du produit" />
                                    <h4>{product.name}</h4>
                                    <p><strong>Description :</strong> {product.description}</p>
                                    <p><strong>Prix :</strong> {product.price} €</p>
                                    {product.collection && (
                                        <p><strong>Collection :</strong> {product.collection.name}</p>
                                    )}
                                </div>
                            ))}
                        </div>
                    </>
                )}
            </div>

            <Footer />
        </div>
    );
}

export default Recherche;
