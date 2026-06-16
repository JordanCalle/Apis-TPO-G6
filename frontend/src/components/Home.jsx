import { Link } from "react-router-dom";
import homeImage from "../assets/Argentina-imagen.jpg";

function Home() {
  return (
    <main>
      <section className="home-hero">
        <h1>Marketplace Grupo 6</h1>
        <p>
          Explorá productos, agregalos al carrito y finalizá tu compra de forma simple.
        </p>

        <img
          className="home-image"
          src={homeImage}
          alt="Imagen principal del marketplace"
        />

        <Link className="home-button" to="/productos">
          Ver productos
        </Link>
      </section>
    </main>
  );
}

export default Home;