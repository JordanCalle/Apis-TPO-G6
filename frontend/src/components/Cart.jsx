import { useSelector, useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { removeFromCart } from "../store/cartSlice";

function Cart() {
  const cartItems = useSelector((state) => state.cart.items);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const total = cartItems.reduce((acc, item) => {
    return acc + Number(item.precio || 0);
  }, 0);

  return (
    <main>
      <h1>Carrito</h1>

      {cartItems.length === 0 ? (
        <section className="carrito-empty">
          <p>El carrito está vacío.</p>

          <Link className="carrito-link" to="/productos">
            Ir a productos
          </Link>
        </section>
      ) : (
        <>
          <section className="carrito-lista">
            {cartItems.map((item, index) => (
              <article className="carrito-card" key={`${item.id}-${index}`}>
                <div className="carrito-info">
                  <h2>{item.nombre || "Producto sin nombre"}</h2>
                  <p>{item.descripcion || "Sin descripción disponible"}</p>
                </div>

                <div className="carrito-actions">
                  <div className="carrito-precio">
                    <span>Precio</span>
                    <strong>${item.precio || 0}</strong>
                  </div>

                  <button
                    className="carrito-remove"
                    onClick={() => dispatch(removeFromCart(index))}
                  >
                    Quitar
                  </button>
                </div>
              </article>
            ))}
          </section>

          <section className="carrito-resumen">
            <h3>Total: ${total}</h3>

            <button onClick={() => navigate("/checkout")}>
              Finalizar compra
            </button>
          </section>
        </>
      )}
    </main>
  );
}

export default Cart;