import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getProductos } from "../services/api";
import { useDispatch, useSelector } from "react-redux";
import { AuthContext } from "../context/AuthProvider";
import {
  loadFavoritesByUser,
  toggleFavorite,
} from "../store/favoritesSlice";

function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const dispatch = useDispatch();
  const favorites = useSelector((state) => state.favorites.items);

  const { user } = useContext(AuthContext);

 "Temppp"
  console.log("Usuario logueado:", user);
  console.log("ID del usuario:", user?.id);

  useEffect(() => {
    const loadProducts = async () => {
      try {
        setLoading(true);
        setError(null);

        const data = await getProductos();
        setProducts(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    loadProducts();
  }, []);

  useEffect(() => {
    if (user) {
      dispatch(loadFavoritesByUser(user.usuarioId));
    }
  }, [user, dispatch]);

  const handleToggleFavorite = (product) => {
    if (!user) {
      alert("Tenés que iniciar sesión para agregar favoritos");
      return;
    }

    dispatch(toggleFavorite({ product, userId: user.usuarioId }));
  };

  if (loading) {
    return <p>Cargando productos...</p>;
  }

  if (error) {
    return <p>Error: {error}</p>;
  }

  return (
    <main>
      <h1>Productos</h1>

      {products.length === 0 ? (
        <p>No hay productos disponibles.</p>
      ) : (
        <section className="productos-grid">
          {products.map((product) => (
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
                onClick={() => handleToggleFavorite(product)}
              >
                {favorites.some((item) => item.id === product.id)
                  ? "Quitar de favoritos"
                  : "Agregar a favoritos"}
              </button>
            </article>
          ))}
        </section>
      )}
    </main>
  );
}

export default ProductList;