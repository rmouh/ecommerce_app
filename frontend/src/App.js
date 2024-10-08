import './styles.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './components/header';
import Footer from './components/Footer';
import Home from './pages/Home/Home';
import Connexion from './pages/Connexion/Connexion';
import Inscription from './pages/Inscription/Inscription';
import Recherche from './pages/Recherche/Recherche';
import Profile from './pages/Profile/Profile';
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
