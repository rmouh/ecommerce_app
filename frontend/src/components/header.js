function Header() {
    return (
        <header>
            <div className="wrapper">
                <h1>Mon E-Commerce<span className="blue">.</span></h1>
                <nav>
                    <ul>
                        <li><a href="/">Accueil</a></li>
                        <li><a href="/product-selection">Sélection de Produits</a></li> {/* Lien vers la sélection de produits */}
                        <li><a href="/recherche">Recherche</a></li>
                        <li><a href="/connexion">Se connecter</a></li>
                        <li><a href="/inscription">S'inscrire</a></li>
                        <li><a href="/profile">Profil</a></li>
                    </ul>
                </nav>
            </div>
        </header>
    );
}

export default Header;
