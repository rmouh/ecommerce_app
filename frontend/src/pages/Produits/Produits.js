import  { useState } from 'react';
import { Link } from 'react-router-dom'; // Assurez-vous que le CSS est bien importé
function Produits() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        // Logique de validation ou redirection après la soumission
        if (email === '' || password === '') {
            setErrorMessage('Veuillez remplir tous les champs.');
        } else {
            // Logique de soumission de formulaire ou d'appel API ici
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
                            <label htmlFor="mot_de_passe">Mot de passe</label>
                            <input
                                type="password"
                                id="mot_de_passe"
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
            </div>
        </section>
    );
}

export default Produits;
