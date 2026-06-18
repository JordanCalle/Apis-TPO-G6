import { useEffect, useState } from "react";
import { useDispatch } from "react-redux";
import { Link, useNavigate, useParams } from "react-router-dom";
import { getProductoById } from "../services/api";
import { addToCart } from "../store/cartSlice";

function ProductDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const loadProduct = async () => {
      try {
        setLoading(true);
        setError(null);

        const data = await getProductoById(id);
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
    dispatch(addToCart(product));
    navigate("/carrito");
  };

  if (loading) {
    return <p className="detalle-status">Cargando detalle del producto...</p>;
  }

  if (error) {
    return (
      <main>
        <section className="detalle-card">
          <p className="detalle-error">Error: {error}</p>

          <Link className="detalle-link" to="/productos">
            Volver a productos
          </Link>
        </section>
      </main>
    );
  }

  if (!product) {
    return (
      <main>
        <section className="detalle-card">
          <p className="detalle-error">No se encontró el producto.</p>

          <Link className="detalle-link" to="/productos">
            Volver a productos
          </Link>
        </section>
      </main>
    );
  }

  return (
    <main>
      <section className="detalle-card">
        <h1>{product.nombre || "Producto sin nombre"}</h1>

        <p className="detalle-descripcion">
          {product.descripcion || "Sin descripción disponible"}
        </p>

        <div className="detalle-precio">
          <span>Precio</span>
          <strong>${product.precio || 0}</strong>
        </div>

        <div className="detalle-meta">
          {product.stock !== undefined && (
            <p>
              <span>Stock disponible:</span> {product.stock}
            </p>
          )}

          {product.categoria && (
            <p>
              <span>Categoría:</span>{" "}
              {product.categoria.nombre || product.categoria}
            </p>
          )}
        </div>

        <div className="detalle-actions">
          <button onClick={handleAddToCart}>
            Agregar al carrito
          </button>

          <Link className="detalle-link" to="/productos">
            Volver a productos
          </Link>
        </div>
      </section>
    </main>
  );
}

export default ProductDetail;