import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getProductos } from "../services/api";

function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

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
        <section>
          {products.map((product) => (
            <article key={product.id}>
              <h2>{product.nombre || "Producto sin nombre"}</h2>

              <p>{product.descripcion || "Sin descripción disponible"}</p>

              <p>Precio: ${product.precio || 0}</p>

              <Link to={`/productos/${product.id}`}>Ver detalle</Link>
            </article>
          ))}
        </section>
      )}
    </main>
  );
}

export default ProductList;