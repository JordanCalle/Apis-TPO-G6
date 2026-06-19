import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  items: [],
};

const getStorageKey = (userId) => `favorites_user_${userId}`;

const favoritesSlice = createSlice({
  name: "favorites",
  initialState,
  reducers: {
    loadFavoritesByUser: (state, action) => {
      const userId = action.payload;

      if (!userId) {
        state.items = [];
        return;
      }

      const storageKey = getStorageKey(userId);
      state.items = JSON.parse(localStorage.getItem(storageKey)) || [];
    },

    toggleFavorite: (state, action) => {
      const { product, userId } = action.payload;

      if (!userId) {
        return;
      }

      const exists = state.items.find((item) => item.id === product.id);

      if (exists) {
        state.items = state.items.filter((item) => item.id !== product.id);
      } else {
        state.items.push(product);
      }

      const storageKey = getStorageKey(userId);
      localStorage.setItem(storageKey, JSON.stringify(state.items));
    },

    removeFavorite: (state, action) => {
      const { productId, userId } = action.payload;

      if (!userId) {
        return;
      }

      state.items = state.items.filter((item) => item.id !== productId);

      const storageKey = getStorageKey(userId);
      localStorage.setItem(storageKey, JSON.stringify(state.items));
    },

    clearFavorites: (state, action) => {
      const userId = action.payload;

      if (!userId) {
        return;
      }

      state.items = [];

      const storageKey = getStorageKey(userId);
      localStorage.setItem(storageKey, JSON.stringify(state.items));
    },

    clearFavoritesFromState: (state) => {
      state.items = [];
    },
  },
});

export const {
  loadFavoritesByUser,
  toggleFavorite,
  removeFavorite,
  clearFavorites,
  clearFavoritesFromState,
} = favoritesSlice.actions;

export default favoritesSlice.reducer;