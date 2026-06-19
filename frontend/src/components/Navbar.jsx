import { useContext, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthProvider";
import {
  clearFavoritesFromState,
  loadFavoritesByUser,
} from "../store/favoritesSlice";

function Navbar() {
  const cartItems = useSelector((state) => state.cart.items);
  const favorites = useSelector((state) => state.favorites.items);

  const { user, logout } = useContext(AuthContext);

  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    if (user?.usuarioId) {
      dispatch(loadFavoritesByUser(user.usuarioId));
    } else {
      dispatch(clearFavoritesFromState());
    }
  }, [user, dispatch]);

  const handleLogout = () => {
    dispatch(clearFavoritesFromState());
    logout();
    navigate("/");
  };

  return (
    <nav>
      <Link to="/">Inicio</Link> |{" "}
      <Link to="/productos">Productos</Link> |{" "}
      <Link to="/carrito">Carrito ({cartItems.length})</Link> |{" "}
      <Link to="/favoritos">Favoritos ({favorites.length})</Link>

      {user ? (
        <>
          {" | "}
          <span>Hola, {user.nombre || user.email}</span>
          {" | "}
          <button
            onClick={handleLogout}
            style={{
              background: "none",
              border: "none",
              cursor: "pointer",
              color: "inherit",
            }}
          >
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