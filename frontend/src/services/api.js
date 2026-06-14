export const API_URL = "http://localhost:8080/api";

const apiCall = async (endpoint, options = {}) => {
  const token = localStorage.getItem("token");
  const headers = {
    "Content-Type": "application/json",
    ...options.headers,
  };

  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  const response = await fetch(`${API_URL}${endpoint}`, {
    ...options,
    headers,
  });

  if (!response.ok) {
    const errorData = await response.json().catch(() => ({}));
    throw new Error(errorData.message || `Error: ${response.status}`);
  }

  return response.json();
};

// ============ AUTH ENDPOINTS ============
export const loginAPI = async (credentials) => {
  return apiCall("/auth/login", {
    method: "POST",
    body: JSON.stringify(credentials),
  });
};

export const registerAPI = async (userData) => {
  return apiCall("/auth/register", {
    method: "POST",
    body: JSON.stringify(userData),
  });
};

// ============ USUARIO ENDPOINTS ============
export const getUsuarios = async () => {
  return apiCall("/usuarios");
};

// ============ PRODUCTO ENDPOINTS ============
export const getProductos = async () => {
  return apiCall("/productos");
};

export const getProductoById = async (id) => {
  return apiCall(`/productos/${id}`);
};

// ============ CARRITO/PEDIDO ENDPOINTS ============
export const createPedido = async (pedidoData) => {
  return apiCall("/pedidos", {
    method: "POST",
    body: JSON.stringify(pedidoData),
  });
};

export const checkoutPedido = async (checkoutData) => {
  return apiCall("/pedidos/checkout", {
    method: "POST",
    body: JSON.stringify(checkoutData),
  });
};

export const getPedidos = async () => {
  return apiCall("/pedidos");
};

export const getPedidoById = async (id) => {
  return apiCall(`/pedidos/${id}`);
};

// ============ CATEGORIA ENDPOINTS ============
export const getCategorias = async () => {
  return apiCall("/categorias");
};
