import { useContext, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { checkoutPedido } from "../services/api";
import { clearCart } from "../store/cartSlice";
import { AuthContext } from "../context/AuthProvider";

function Checkout() {
  const cartItems = useSelector((state) => state.cart.items);
  const dispatch = useDispatch();
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
      dispatch(clearCart());

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

if (cartItems.length === 0 && !successMessage) {
  return (
    <main>
      <h1>Checkout</h1>

      <section className="checkout-empty">
        <p>No hay productos en el carrito para comprar.</p>

        <Link className="checkout-link" to="/productos">
          Volver a productos
        </Link>
      </section>
    </main>
  );
}

return (
  <main>
    <h1>Checkout</h1>

    {error && <p className="checkout-error">Error: {error}</p>}
    {successMessage && <p className="checkout-success">{successMessage}</p>}

    {!successMessage && (
      <form className="checkout-layout" onSubmit={handleCheckout}>
        <section className="checkout-lista">
          <h2>Resumen de compra</h2>

          {cartItems.map((item, index) => (
            <article className="checkout-card" key={`${item.id}-${index}`}>
              <div className="checkout-info">
                <h3>{item.nombre || "Producto sin nombre"}</h3>
                <p>Cantidad: 1</p>
              </div>

              <div className="checkout-precio">
                <span>Precio</span>
                <strong>${item.precio || 0}</strong>
              </div>
            </article>
          ))}
        </section>

        <section className="checkout-form-card">
          <h2>Método de pago</h2>

          <div className="checkout-field">
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
          </div>

          {pago.metodo === "TARJETA_CREDITO" && (
            <div className="checkout-card-fields">
              <div className="checkout-field">
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

              <div className="checkout-field">
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

              <div className="checkout-field">
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

              <div className="checkout-field">
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

        <section className="checkout-resumen">
          <h3>Total: ${total}</h3>

          <button type="submit" disabled={loading}>
            {loading ? "Procesando compra..." : "Confirmar compra"}
          </button>
        </section>
      </form>
    )}

    <Link className="checkout-back-link" to="/carrito">
      Volver al carrito
    </Link>
  </main>
);
}

export default Checkout;
