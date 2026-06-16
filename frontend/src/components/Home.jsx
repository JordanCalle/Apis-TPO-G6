import { Link } from "react-router-dom";

function Home() {
  return (
    <main>
      <h1>E-commerce Grupo 6 </h1>
      <p>Bienvenido al marketplace del sponsor oficial de la selección Argentina</p>
      <Link to="/productos">Ver productos</Link>
    </main>
  );
}

export default Home;