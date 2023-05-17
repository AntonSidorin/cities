import { configureStore } from '@reduxjs/toolkit';
import { apiSlice } from 'api/apiSlice';
import authReducer from 'store/auth/authSlice';
import filterReducer from 'store/filter/filterSlice';

export const store = configureStore({
  reducer: {
    [apiSlice.reducerPath]: apiSlice.reducer,
    auth: authReducer,
    filter: filterReducer,
  },
  middleware: getDefaultMiddleware => getDefaultMiddleware().concat(apiSlice.middleware),
  devTools: true
});
