import { apiSlice } from 'api/apiSlice';

export const cityApiSlice = apiSlice.injectEndpoints({
  tagTypes: ['City'],
  keepUnusedDataFor: 30,
  endpoints: builder => ({
    getCities: builder.query({
      query: ({
                page = 0,
                name,
              }) => name.length > 0 ? `/api/v1/cities?page=${page}&name=${name}` : `/api/v1/cities?page=${page}`,
      keepUnusedDataFor: 0,
      providesTags: (result) =>
        result?.cities
          ? [...result.cities.map(({ id }) => ({ type: 'City', id })), { type: 'City', id: 'PARTIAL-LIST' }]
          : [{ type: 'City', id: 'PARTIAL-LIST' }],
    }),
    getCity: builder.query({
      query: id => `/api/v1/cities/${id}`,
      providesTags: (result) => [{ type: 'City', id: result.id }],
    }),
    updateCity: builder.mutation({
      query: ({ id, name, photo }) => ({
        url: `/api/v1/cities/${id}`,
        method: 'PATCH',
        body: { id, name, photo },
      }),
      async onQueryStarted({ id, ...patch }, { dispatch, queryFulfilled }) {
        try {
          const { data: updatedCity } = await queryFulfilled;
          dispatch(
            cityApiSlice.util.updateQueryData('getCity', id, (draft) => {
              Object.assign(draft, updatedCity);
            }),
          );
        } catch { }
      },
    }),
  }),
});

export const {
  useGetCitiesQuery,
  useGetCityQuery,
  useUpdateCityMutation,
} = cityApiSlice;