import { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { API_URL } from "../services/api";
import { CartContext } from "../context/CartProvider";

function Checkout() {
  const { cartItems, clearCart } = useContext(CartContext);
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState("");

  const total = cartItems.reduce((acc, item) => {
    return acc + Number(item.precio || 0);
  }, 0);

  const handleCheckout = async () => {
    try {
      setLoading(true);
      setError(null);
      setSuccessMessage("");
      //testeo para el checkout
  const checkoutData = {
    usuarioId: 1,
    items: cartItems.map((item) => ({
      productoId: item.id,
      cantidad: 1,
    })),
    pago: {
      metodo: "TARJETA_CREDITO",
      numeroTarjeta: "4111111111111111",
      titular: "Usuario Demo",
      fechaVencimiento: "12/28",
      cvv: "123",
    },
  };

      const response = await fetch(`${API_URL}/pedidos/checkout`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(checkoutData),
      });
      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(
          `No se pudo finalizar la compra. Status: ${response.status}. Respuesta: ${errorText}`
        );
      }

      setSuccessMessage("Compra realizada correctamente");
      clearCart();

      setTimeout(() => {
        navigate("/productos");
      }, 1500);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  if (cartItems.length === 0 && !successMessage) {
    return (
      <main>
        <h1>Checkout</h1>
        <p>No hay productos en el carrito para comprar.</p>
        <Link to="/productos">Volver a productos</Link>
      </main>
    );
  }

  return (
    <main>
      <h1>Checkout</h1>

      {error && <p>Error: {error}</p>}

      {successMessage && <p>{successMessage}</p>}

      {!successMessage && (
        <section>
          <h2>Resumen de compra</h2>

          {cartItems.map((item, index) => (
            <article key={`${item.id}-${index}`}>
              <h3>{item.nombre || "Producto sin nombre"}</h3>
              <p>Precio: ${item.precio || 0}</p>
              <p>Cantidad: 1</p>
            </article>
          ))}

          <h3>Total: ${total}</h3>

          <button onClick={handleCheckout} disabled={loading}>
            {loading ? "Procesando compra..." : "Confirmar compra"}
          </button>
        </section>
      )}

      <br />

      <Link to="/carrito">Volver al carrito</Link>
    </main>
  );
}

export default Checkout;