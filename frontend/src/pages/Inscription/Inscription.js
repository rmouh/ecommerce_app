import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Inscription() {
    const [formData, setFormData] = useState({
        email: '',
        password: '',
        firstName: '',
        lastName: '',
        address: '',
        phoneNumber: '',
        username: '' // Ajout de username
    });

    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // Appel de l'API backend pour s'inscrire
            const response = await axios.post('http://localhost:8080/account/register', {
                adress: formData.adress,
                firstName: formData.firstName,
                lastName: formData.lastName,
                username: formData.username,
                email: formData.email,
                password: formData.password
            });

            // Si l'inscription réussit
            setMessage('Inscription réussie!');
            navigate('/connexion'); // Redirige vers la page de connexion après inscription
        } catch (error) {
            if (error.response && error.response.status === 400) {
                setMessage(error.response.data);
            } else {
                setMessage('Erreur lors de l\'inscription. Veuillez réessayer.');
            }
        }
    };

    return (
        <section id="main-image">
            <div className="container2">
                <div className="signin-box">
                    <h3>Inscription</h3>
                    <div id="message-container" style={{ color: 'red' }}>{message}</div>
                    <form onSubmit={handleSubmit}>
                        {/* Prénom */}
                        <div className="half-width">
                            <label htmlFor="firstName">Prénom</label>
                            <input type="text" id="firstName" name="firstName" value={formData.firstName} onChange={handleChange} required />
                        </div>

                        {/* Nom */}
                        <div className="half-width">
                            <label htmlFor="lastName">Nom</label>
                            <input type="text" id="lastName" name="lastName" value={formData.lastName} onChange={handleChange} required />
                        </div>

                        {/* Nom d'utilisateur */}
                        <div className="half-width">
                            <label htmlFor="username">Nom d'utilisateur</label>
                            <input type="text" id="username" name="username" value={formData.username} onChange={handleChange} required />
                        </div>

                        {/* Adresse */}
                        <div className="half-width">
                            <label htmlFor="address">Adresse</label>
                            <input type="text" id="address" name="address" value={formData.address} onChange={handleChange} required />
                        </div>

                        {/* Email */}
                        <div className="half-width">
                            <label htmlFor="email">Adresse e-mail</label>
                            <input type="email" id="email" name="email" value={formData.email} onChange={handleChange} required />
                        </div>

                        {/* Mot de passe */}
                        <div className="half-width">
                            <label htmlFor="password">Mot de passe</label>
                            <input type="password" id="password" name="password" value={formData.password} onChange={handleChange} required />
                        </div>

                        <button type="submit" className="button-3">S'inscrire</button>
                    </form>
                    <p>Déjà inscrit ? <a href="/connexion">Se connecter</a></p>
                </div>
            </div>
        </section>
    );
}

export default Inscription;
