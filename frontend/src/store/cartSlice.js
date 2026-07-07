import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { checkoutPedido } from "../services/api";

const initialState = {
  items: [],
  checkoutStatus: "idle",
  checkoutError: null,
};

export const checkoutCart = createAsyncThunk(
  "cart/checkoutCart",
  async ({ checkoutData, userId }, { dispatch, rejectWithValue }) => {
    try {
      await checkoutPedido(checkoutData);
      dispatch(clearCart(userId));
      return { success: true };
    } catch (error) {
      return rejectWithValue(error.message || "Error al procesar la compra");
    }
  },
);

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addToCart: (state, action) => {
      state.items.push(action.payload);
    },
    removeFromCart: (state, action) => {
      state.items.splice(action.payload, 1);
    },
    clearCart: (state, action) => {
      state.items = [];
      state.checkoutStatus = "idle";
      state.checkoutError = null;
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(checkoutCart.pending, (state) => {
        state.checkoutStatus = "loading";
        state.checkoutError = null;
      })
      .addCase(checkoutCart.fulfilled, (state) => {
        state.checkoutStatus = "succeeded";
        state.checkoutError = null;
      })
      .addCase(checkoutCart.rejected, (state, action) => {
        state.checkoutStatus = "failed";
        state.checkoutError = action.payload;
      });
  },
});

export const { addToCart, removeFromCart, clearCart } = cartSlice.actions;
export default cartSlice.reducer;
