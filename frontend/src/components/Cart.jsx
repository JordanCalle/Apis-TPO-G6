import { useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import { CartContext } from "../context/CartProvider";

function Cart() {
  const { cartItems } = useContext(CartContext);
  const navigate = useNavigate();

  const total = cartItems.reduce((acc, item) => {
    return acc + Number(item.precio || 0);
  }, 0);

  return (
    <main>
      <h1>Carrito</h1>

      {cartItems.length === 0 ? (
        <section>
          <p>El carrito está vacío.</p>
          <Link to="/productos">Ir a productos</Link>
        </section>
      ) : (
        <section>
          {cartItems.map((item, index) => (
            <article key={`${item.id}-${index}`}>
              <h2>{item.nombre || "Producto sin nombre"}</h2>
              <p>{item.descripcion || "Sin descripción disponible"}</p>
              <p>Precio: ${item.precio || 0}</p>
            </article>
          ))}

          <h3>Total: ${total}</h3>

          <button onClick={() => navigate("/checkout")}>
            Finalizar compra
          </button>
        </section>
      )}
    </main>
  );
}

export default Cart;