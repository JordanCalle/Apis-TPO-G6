import { useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthProvider";
import { useContext } from "react";

function Navbar() {
  const cartItems = useSelector((state) => state.cart.items);
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <nav>
      <Link to="/">Inicio</Link> |{" "}
      <Link to="/productos">Productos</Link> |{" "}
      <Link to="/carrito">Carrito ({cartItems.length})</Link>
      {user ? (
        <>
          {" | "}
          <span>Hola, {user.nombre || user.email}</span>
          {" | "}
          <button onClick={handleLogout} style={{ background: "none", border: "none", cursor: "pointer", color: "inherit" }}>
            Cerrar Sesión
          </button>
        </>
      ) : (
        <>
          {" | "}
          <Link to="/login">Iniciar Sesión</Link>
          {" | "}
          <Link to="/register">Registrarse</Link>
        </>
      )}
    </nav>
  );
}

export default Navbar;