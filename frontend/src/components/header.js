
function Header() {
    return (
        <header>
            <div className="wrapper">
                <h1>Mon E-Commerce<span className="blue">.</span></h1>
                <nav>
                    <ul>
                        <li><a href="/">Accueil</a></li>
                        <li><a href="/Recherche">Recherche</a></li>
                        <li><a href="/connexion">Se connecter</a></li>
                        <li><a href="/inscription">Sinscrire</a></li>
                        <li><a href="/Profile">Profile</a></li>
                    </ul>
                </nav>
            </div>
        </header>
    );
}

export default Header;
