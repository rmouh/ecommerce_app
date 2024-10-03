import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function Profile() {
    const [userInfo, setUserInfo] = useState({
        firstName: '',
        lastName: '',
        email: '',
        address: '',
        password: '' // Champ pour le mot de passe
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const token = localStorage.getItem('token');

                // Vérification si le token existe, sinon redirection vers la page de connexion
                if (!token) {
                    navigate('/connexion');
                    return;
                }

                // Appel de l'API pour obtenir les informations de l'utilisateur
                const response = await axios.get('http://localhost:8080/account/profil', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });

                setUserInfo(response.data.User);
                setLoading(false);
            } catch (error) {
                setLoading(false);
                if (error.response && error.response.status === 401) {
                    // Si non autorisé (ex. token expiré), redirige vers la page de connexion
                    navigate('/connexion');
                } else {
                    setError('Erreur lors de la récupération des informations de l\'utilisateur.');
                }
            }
        };

        fetchUserInfo();
    }, [navigate]);

    const handleChange = (e) => {
        setUserInfo({
            ...userInfo,
            [e.target.name]: e.target.value
        });
    };

    const handleUpdate = async (e) => {
        e.preventDefault();
        setMessage('');
        setError('');

        try {
            const token = localStorage.getItem('token');

            // Appel de l'API pour mettre à jour les informations de l'utilisateur
            const response = await axios.put('http://localhost:8080/account/update', userInfo, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            setMessage('Informations mises à jour avec succès.');
            setUserInfo(response.data); // Mettre à jour les infos après sauvegarde
        } catch (error) {
            if (error.response && error.response.status === 400) {
                setError('Erreur lors de la mise à jour. Veuillez vérifier les champs.');
            } else {
                setError('Erreur interne du serveur. Veuillez réessayer plus tard.');
            }
        }
    };

    if (loading) {
        return <div>Chargement...</div>;
    }

    if (error) {
        return <div style={{ color: 'red' }}>{error}</div>;
    }

    return (
        <section id="main-image">
            <div className="container2">
                <div className="signin-box">
                    <h3>Profil de l'utilisateur</h3>
                    {message && <p style={{ color: 'green' }}>{message}</p>}
                    {error && <p style={{ color: 'red' }}>{error}</p>}
                    <form onSubmit={handleUpdate}>
                        {/* Prénom */}
                        <div className="input-group">
                            <label htmlFor="firstName">Prénom</label>
                            <input
                                type="text"
                                id="firstName"
                                name="firstName"
                                value={userInfo.firstName}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        {/* Nom */}
                        <div className="input-group">
                            <label htmlFor="lastName">Nom</label>
                            <input
                                type="text"
                                id="lastName"
                                name="lastName"
                                value={userInfo.lastName}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        {/* Adresse Email */}
                        <div className="input-group">
                            <label htmlFor="email">Adresse e-mail</label>
                            <input
                                type="email"
                                id="email"
                                name="email"
                                value={userInfo.email}
                                onChange={handleChange}
                                required
                            />
                        </div>

                        {/* Adresse */}
                        <div className="input-group">
                            <label htmlFor="address">Adresse</label>
                            <input
                                type="text"
                                id="address"
                                name="address"
                                value={userInfo.address}
                                onChange={handleChange}
                            />
                        </div>

                        {/* Mot de passe */}
                        <div className="input-group">
                            <label htmlFor="password">Mot de passe</label>
                            <input
                                type="password"
                                id="password"
                                name="password"
                                value={userInfo.password}
                                onChange={handleChange}
                            />
                        </div>

                        <button type="submit">Enregistrer les modifications</button>
                    </form>
                </div>
            </div>
        </section>
    );
}

export default Profile;
