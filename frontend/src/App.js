import './styles.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './components/header';
import Footer from './components/Footer';

import Home from './pages/Home/Home';          // Page d'accueil
import Connexion from './pages/Connexion/Connexion';  // Page Connexion
import Inscription from './pages/Inscription/Inscription'; // Page Inscription
import Recherche from './pages/Recherche/Recherche';  // Page Recherche
import Profile from './pages/Profile/Profile';   // Page Profile
import Article from './pages/Article/Article';   // Page Article
import Panier from './pages/Panier/Panier';      // Page Panier
import Commande from './pages/Commande/Commande';
import ProductSelection from './pages/ProductSelection/ProductSelection'; // Importer le nouveau composant
import PaymentPage from './pages/Payment/PaymentPage';
import Checkout from './pages/Checkout/Checkout'; // Import du Checkout
import PaymentSuccess from './pages/Payment/PaymentSuccess';
import PaymentFailure from './pages/Payment/PaymentFailure';


function App() {
    return (
        <Router>
            <Header />
            <Routes>
                {/* Route pour la page d'accueil */}
                <Route path="/" element={<Home />} />

                {/* Route pour la sélection de produit */}
                <Route path="/product-selection" element={<ProductSelection />} />

                {/* Route pour la page de recherche */}
                <Route path="/recherche" element={<Recherche />} />

                {/* Route pour la page de profil */}
                <Route path="/profile" element={<Profile />} />

                {/* Route pour la page de connexion */}
                <Route path="/connexion" element={<Connexion />} />

                {/* Route pour la page d'inscription */}
                <Route path="/inscription" element={<Inscription />} />


                {/* Route pour la page du panier */}
                <Route path="/panier" element={<Panier />} />

                <Route path="/commande" element={<Commande />} />


                {/* Route pour le Checkout */}
                <Route path="/checkout" element={<Checkout />} />

                {/* Route pour la page de paiement */}
                <Route path="/payment" element={<PaymentPage />} />

                {/* Routes pour les pages de succès et d'échec de paiement */}
                <Route path="/payment-success" element={<PaymentSuccess />} />
                <Route path="/payment-failure" element={<PaymentFailure />} />

            </Routes>
            <Footer />
        </Router>
    );
}

export default App;
