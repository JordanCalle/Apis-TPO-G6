import { useContext, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { API_URL } from "../services/api";
import { CartContext } from "../context/CartProvider";

function ProductDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { addToCart } = useContext(CartContext);

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadProduct = async () => {
      try {
        setLoading(true);
        setError(null);

        const response = await fetch(`${API_URL}/productos/${id}`);

        if (!response.ok) {
          throw new Error("No se pudo cargar el detalle del producto");
        }

        const data = await response.json();
        setProduct(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    loadProduct();
  }, [id]);

  const handleAddToCart = () => {
    addToCart(product);
    navigate("/carrito");
  };

  if (loading) {
    return <p>Cargando detalle del producto...</p>;
  }

  if (error) {
    return (
      <main>
        <p>Error: {error}</p>
        <Link to="/productos">Volver a productos</Link>
      </main>
    );
  }

  if (!product) {
    return (
      <main>
        <p>No se encontró el producto.</p>
        <Link to="/productos">Volver a productos</Link>
      </main>
    );
  }

  return (
    <main>
      <h1>{product.nombre || "Producto sin nombre"}</h1>

      <p>{product.descripcion || "Sin descripción disponible"}</p>

      <p>Precio: ${product.precio || 0}</p>

      {product.stock !== undefined && (
        <p>Stock disponible: {product.stock}</p>
      )}

      {product.categoria && (
        <p>Categoría: {product.categoria.nombre || product.categoria}</p>
      )}

      <button onClick={handleAddToCart}>
        Agregar al carrito
      </button>

      <br />
      <br />

      <Link to="/productos">Volver a productos</Link>
    </main>
  );
}

export default ProductDetail;