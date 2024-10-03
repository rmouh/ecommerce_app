
function Contact() {
    return (
        <section id="contact">
            <div className="wrapper">
                <h3>Contactez-nous</h3>
                <p>Besoin d aide pour choisir un produit ou une question sur une commande ? Contactez notre service client.</p>
                <form>
                    <label htmlFor="email">Adresse e-mail</label>
                    <input type="email" id="email" name="email" required />
                    <label htmlFor="commentaire">Votre message</label>
                    <input type="text" id="commentaire" name="commentaire" required />
                    <input type="submit" value="Envoyer" className="button-3" />
                </form>
            </div>
        </section>
    );
}

export default Contact;
