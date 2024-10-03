

function Products() {
    return (
        <section id="possibilities">
            <div className="wrapper">
                <h3>Produits populaires</h3>
                <div className="product-row">
                    <article className="product" style={{ backgroundImage: "url(public/images/livre.jpg)" }}>
                        <div className="overlay">
                            <h4>Produit 1</h4>
                            <a href="produit1.html" className="button-2">Voir les détails</a>
                        </div>
                    </article>
                    <article className="product" style={{ backgroundImage: "url(public/images/livre.jpg)" }}>
                        <div className="overlay">
                            <h4>Produit 2</h4>
                            <a href="produit2.html" className="button-2">Voir les détails</a>
                        </div>
                    </article>
                </div>
            </div>
        </section>
    );
}

export default Products;
