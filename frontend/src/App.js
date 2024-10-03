import './styles.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './components/header';
import Footer from './components/Footer';
import Home from './pages/Home/Home';       // Page d'accueil
import Connexion from './pages/Connexion/Connexion'; // Page Connexion
import Inscription from './pages/Inscription/Inscription'; // Page Inscription
import Recherche from './pages/Recherche/Recherche';
import Profile from './pages/Profile/Profile';

function App() {
  return (
      <Router>
        <Header />
        <Routes>
          {/* Route pour la page d'accueil */}
          <Route path="/" element={<Home />} />

          {/* Route pour la page produits */}
          <Route path="/Recherche" element={<Recherche />} />

            {/* Route pour la page produits */}
            <Route path="/Profile" element={<Profile />} />

          {/* Route pour la page connexion */}
          <Route path="/connexion" element={<Connexion />} />

          {/* Route pour la page inscription */}
          <Route path="/inscription" element={<Inscription />} />
        </Routes>
        <Footer />
      </Router>
  );
}

export default App;
