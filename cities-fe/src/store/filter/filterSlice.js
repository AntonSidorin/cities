import { createSlice } from '@reduxjs/toolkit';

const filterSlice = createSlice({
  name: 'filter',
  initialState: { page: 1, name: '' },
  reducers: {
    setFilter: (state, action) => {
      const { page, name } = action.payload;
      state.page = page;
      state.name = name;
    },
    clearFilter: (state) => {
      state.page = 1;
      state.name = '';
    },
  },
});

export const { setFilter, clearFilter } = filterSlice.actions;

export default filterSlice.reducer;

export const selectCurrentPage = (state) => state.filter.page;
export const selectCurrentName = (state) => state.filter.name;
