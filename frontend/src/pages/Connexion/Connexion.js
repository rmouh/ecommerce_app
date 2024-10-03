import { Link, useNavigate } from 'react-router-dom'; // Importer useNavigate
import { useState } from 'react';
import axios from 'axios';

function Connexion() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate(); // Initialiser useNavigate

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Validation de base
        if (username === '' || password === '') {
            setErrorMessage('Veuillez remplir tous les champs.');
            return;
        }

        try {
            // Appel de l'API backend pour se connecter
            const response = await axios.post('http://localhost:8080/account/login', {
                username: username,
                password: password
            });

            // Si la connexion réussit, vous pouvez sauvegarder le token JWT par exemple
            const token = response.data.token;
            localStorage.setItem('token', token); // Sauvegarder le token JWT

            console.log('Connexion réussie :', response.data);
            setErrorMessage('');

            // Redirection vers la page d'accueil
            navigate('/Profile');
        } catch (error) {
            if (error.response && error.response.status === 400) {
                setErrorMessage(error.response.data);
            } else {
                setErrorMessage('Erreur lors de la connexion. Veuillez réessayer.');
            }

            // Redirection vers la page d'inscription si la connexion échoue
            navigate('/inscription');
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
                            <label htmlFor="username">Nom d'utilisateur</label>
                            <input
                                type="text"
                                id="username"
                                name="username"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
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

export default Connexion;
