import axios from 'axios';

// Fonction pour récupérer le profil de l'utilisateur
export const fetchUserProfile = async () => {
    const token = localStorage.getItem('token');
    try {
        const response = await axios.get('http://localhost:8080/account/profil', {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        return response.data; // Retourne les données du profil
    } catch (error) {
        console.error('Erreur lors de la récupération du profil utilisateur :', error);
        throw error; // Relance l'erreur pour la gestion dans le composant appelant
    }
};
