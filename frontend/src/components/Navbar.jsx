import { useContext } from "react";
import { Link } from "react-router-dom";
import { CartContext } from "../context/CartProvider";

function Navbar() {
  const { cartItems } = useContext(CartContext);

  return (
    <nav>
      <Link to="/">Inicio</Link> |{" "}
      <Link to="/productos">Productos</Link> |{" "}
      <Link to="/carrito">Carrito ({cartItems.length})</Link>
    </nav>
  );
}

export default Navbar;