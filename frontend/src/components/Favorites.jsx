import { useContext } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { AuthContext } from "../context/AuthProvider";
import { clearFavorites, removeFavorite } from "../store/favoritesSlice";

function Favorites() {
  const dispatch = useDispatch();
  const favorites = useSelector((state) => state.favorites.items);

  const { user } = useContext(AuthContext);

  const handleClearFavorites = () => {
    if (!user) {
      alert("Tenés que iniciar sesión para vaciar favoritos");
      return;
    }

    dispatch(clearFavorites(user.usuarioId));
  };

  const handleRemoveFavorite = (productId) => {
    if (!user) {
      alert("Tenés que iniciar sesión para quitar favoritos");
      return;
    }

    dispatch(removeFavorite({ productId, userId: user.usuarioId }));
  };

  return (
    <main>
      <h1>Favoritos</h1>

      {!user ? (
        <p>Tenés que iniciar sesión para ver tus favoritos.</p>
      ) : favorites.length === 0 ? (
        <p>No tenés productos favoritos todavía.</p>
      ) : (
        <>
          <button
            className="producto-link"
            onClick={handleClearFavorites}
          >
            Vaciar favoritos
          </button>

          <section className="productos-grid">
            {favorites.map((product) => (
              <article className="producto-card" key={product.id}>
                <h2>{product.nombre || "Producto sin nombre"}</h2>

                <p>{product.descripcion || "Sin descripción disponible"}</p>

                <p className="producto-precio">
                  Precio: ${product.precio || 0}
                </p>

                <Link className="producto-link" to={`/productos/${product.id}`}>
                  Ver detalle
                </Link>

                <button
                  className="producto-link"
                  onClick={() => handleRemoveFavorite(product.id)}
                >
                  Quitar de favoritos
                </button>
              </article>
            ))}
          </section>
        </>
      )}
    </main>
  );
}

export default Favorites;