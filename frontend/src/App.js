import './styles.css';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Header from './components/header';
import Footer from './components/Footer';

import Home from './pages/Home/Home';
import Connexion from './pages/Connexion/Connexion';
import Inscription from './pages/Inscription/Inscription';
import Recherche from './pages/Recherche/Recherche';
import Profile from './pages/Profile/Profile';
import PaymentPage from './pages/Payment/PaymentPage';
import PaymentSuccess from './pages/Payment/PaymentSuccess';
import PaymentFailure from './pages/Payment/PaymentFailure';

function App() {
    return (
        <Router>
            <Header />
            <Routes>
                {/* Route pour la page d'accueil */}
                <Route path="/" element={<Home />} />

                {/* Route pour la page de recherche */}
                <Route path="/recherche" element={<Recherche />} />

                {/* Route pour la page de profil */}
                <Route path="/profile" element={<Profile />} />

                {/* Route pour la page de connexion */}
                <Route path="/connexion" element={<Connexion />} />

                {/* Route pour la page d'article avec un ID */}
                <Route path="/article/:id" element={<Article />} />

                {/* Route pour la page d'inscription */}
                <Route path="/inscription" element={<Inscription />} />
    {/*
                <Route path="/" element={<Navigate to="/home" />} />
                <Route path="/home" element={<Home />} />
                <Route path="/payment" element={<PaymentPage />} />
                <Route path="/payment-success" element={<PaymentSuccess />} />
                <Route path="/payment-failure" element={<PaymentFailure />} />
                <Route path="/recherche" element={<Recherche />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/connexion" element={<Connexion />} />
*/}

            </Routes>
            <Footer />
        </Router>
    );
}

export default App;
