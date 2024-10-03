import Header from '../../components/header';
import MainImage from "./MainImage";
import Steps from "./Steps";
import Products from "./Products";
import Contact from '../../components/contact';
import Footer from '../../components/Footer';


function Home() {
    return (
        <div>
            <Header />
            <MainImage />
            <Steps />
            <Products />
            <Contact />
            <Footer />
        </div>
    )
}

export default Home;
