import { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { checkoutPedido } from "../services/api";
import { CartContext } from "../context/CartProvider";
import { AuthContext } from "../context/AuthProvider";

function Checkout() {
  const { cartItems, clearCart } = useContext(CartContext);
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState("");

  const [pago, setPago] = useState({
    metodo: "",
    numeroTarjeta: "",
    titular: "",
    fechaVencimiento: "",
    cvv: "",
  });

  const total = cartItems.reduce((acc, item) => {
    return acc + Number(item.precio || 0);
  }, 0);

  const handleMetodoChange = (event) => {
    setPago({
      metodo: event.target.value,
      numeroTarjeta: "",
      titular: "",
      fechaVencimiento: "",
      cvv: "",
    });
  };

  const handlePagoChange = (event) => {
    const { name, value } = event.target;

    setPago((prevPago) => ({
      ...prevPago,
      [name]: value,
    }));
  };

  const handleCheckout = async (event) => {
    event.preventDefault();

    if (!user) {
      setError("Debes estar autenticado para realizar una compra");
      navigate("/login");
      return;
    }

    try {
      setLoading(true);
      setError(null);
      setSuccessMessage("");

      const pagoData =
        pago.metodo === "TARJETA_CREDITO"
          ? {
              ...pago,
              numeroTarjeta: pago.numeroTarjeta.replace(/\s/g, ""),
            }
          : {
              metodo: pago.metodo,
            };

      const checkoutData = {
        usuarioId: user.usuarioId,
        items: cartItems.map((item) => ({
          productoId: item.id,
          cantidad: 1,
        })),
        pago: pagoData,
      };

      await checkoutPedido(checkoutData);

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
        <form onSubmit={handleCheckout}>
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
          </section>

          <section>
            <h2>Método de pago</h2>

            <label htmlFor="metodo">Seleccioná un método:</label>
            <select
              id="metodo"
              name="metodo"
              value={pago.metodo}
              onChange={handleMetodoChange}
              required
            >
              <option value="">Seleccionar</option>
              <option value="TARJETA_CREDITO">Tarjeta de crédito</option>
              <option value="TRANSFERENCIA">Transferencia bancaria</option>
              <option value="MERCADO_PAGO">Mercado Pago</option>
            </select>

            {pago.metodo === "TARJETA_CREDITO" && (
              <div>
                <div>
                  <label htmlFor="numeroTarjeta">Número de tarjeta:</label>
                  <input
                    id="numeroTarjeta"
                    name="numeroTarjeta"
                    type="text"
                    inputMode="numeric"
                    maxLength="19"
                    value={pago.numeroTarjeta}
                    onChange={handlePagoChange}
                    required
                  />
                </div>

                <div>
                  <label htmlFor="titular">Titular:</label>
                  <input
                    id="titular"
                    name="titular"
                    type="text"
                    value={pago.titular}
                    onChange={handlePagoChange}
                    required
                  />
                </div>

                <div>
                  <label htmlFor="fechaVencimiento">Vencimiento:</label>
                  <input
                    id="fechaVencimiento"
                    name="fechaVencimiento"
                    type="text"
                    placeholder="MM/AA"
                    maxLength="5"
                    value={pago.fechaVencimiento}
                    onChange={handlePagoChange}
                    required
                  />
                </div>

                <div>
                  <label htmlFor="cvv">CVV:</label>
                  <input
                    id="cvv"
                    name="cvv"
                    type="password"
                    inputMode="numeric"
                    maxLength="4"
                    value={pago.cvv}
                    onChange={handlePagoChange}
                    required
                  />
                </div>
              </div>
            )}
          </section>

          <button type="submit" disabled={loading}>
            {loading ? "Procesando compra..." : "Confirmar compra"}
          </button>
        </form>
      )}

      <br />
      <Link to="/carrito">Volver al carrito</Link>
    </main>
  );
}

export default Checkout;
