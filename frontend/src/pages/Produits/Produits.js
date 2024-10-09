import { useState } from 'react';
import { Link } from 'react-router-dom';

function Products() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    // Liste de produits statique pour les tests
    const products = [
        { name: 'Produit 1' },
        { name: 'Produit 2' },
        { name: 'Produit 3' },
        { name: 'Produit 4' }
    ];

    const handleSubmit = (e) => {
        e.preventDefault();
        if (email === '' || password === '') {
            setErrorMessage('Veuillez remplir tous les champs.');
        } else {
            setErrorMessage('');
            console.log('Connexion réussie avec :', email, password);
        }
    };

    return (
        <section id="main-image">
            <div className="container">
                <div className="login-box">
                    <h3>Connexion</h3>
                    {errorMessage && <p className="error-message">{errorMessage}</p>}
                    <form onSubmit={handleSubmit}>
                        <div className="input-group">
                            <label htmlFor="email">Adresse e-mail</label>
                            <input
                                type="email"
                                id="email"
                                name="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                            />
                        </div>
                        <div className="input-group">
                            <label htmlFor="password">Mot de passe</label>
                            <input
                                type="password"
                                id="password"
                                name="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                        </div>
                        <button type="submit">Se connecter</button>
                    </form>
                    <p>Pas encore inscrit ? <Link to="/inscription">S'inscrire</Link></p>
                </div>

                <div className="products-list">
                    <h3>Liste des Produits</h3>
                    {Array.isArray(products) && products.length > 0 ? (
                        <ul>
                            {products.map((product, index) => (
                                <li key={index}>{product.name}</li>
                            ))}
                        </ul>
                    ) : (
                        <p>Aucun produit disponible.</p>
                    )}
                </div>
            </div>
        </section>
    );
}

export default Products;
