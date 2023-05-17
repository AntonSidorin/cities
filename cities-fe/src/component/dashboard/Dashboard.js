import { selectCurrentUser } from 'store/auth/authSlice';
import { useGetCitiesQuery } from 'api/city/cityApiSlice';
import Loading from 'component/util/Loading';
import LogOutWidget from 'component/city/widget/LogOutWidget';
import CityWidgets from 'component/city/widget/CityWidgets';
import Error from 'component/error/Error';
import RefreshDashboardWidget from 'component/city/widget/RefreshDashboardWidget';
import { useDispatch, useSelector } from 'react-redux';
import { useRef } from 'react';
import { selectCurrentName, selectCurrentPage, setFilter } from 'store/filter/filterSlice';

const Dashboard = () => {

  const nameRef = useRef();

  const user = useSelector(selectCurrentUser);
  const page = useSelector(selectCurrentPage);
  const name = useSelector(selectCurrentName);

  const dispatch = useDispatch();

  const welcome = user ? `Welcome ${user.firstname} ${user.lastname}` : 'Welcome';

  const {
    data,
    refetch,
    isLoading,
    isSuccess,
  } = useGetCitiesQuery({ page: page - 1, name });

  const handlePrevious = () => {
    if (page > 1) {
      dispatch(setFilter({ page: page - 1, name }));
    }
  };

  const handleNext = () => {
    if (page < data?.totalPages) {
      dispatch(setFilter({ page: page + 1, name }));
    }
  };

  const handleCityNameInput = () => {
    dispatch(setFilter({ page: 1, name: nameRef.current.value }));
  };

  if (isLoading) return <Loading />;

  return (
    <section className="dashboard">
      <section className="info">
        <h1>{welcome}</h1>
      </section>
      <section className="cities">
        <div className="cityActionWidgets">
          <RefreshDashboardWidget refetch={refetch} />
          <LogOutWidget />
        </div>
        <div className="citiesNavigation">
          <button className="previous" disabled={page === 1} onClick={handlePrevious}>
            Previous
          </button>
          <button className="next" disabled={page === data?.totalPages} onClick={handleNext}>
            Next
          </button>
          <span>{page}/{data?.totalPages}</span>
          <input
            ref={nameRef}
            type="text"
            id="city_name"
            placeholder="City name"
            maxLength="200"
            defaultValue={name}
            autoComplete="off"
          />
          <button className="search" onClick={handleCityNameInput}>Search</button>
        </div>
        {isSuccess ? <CityWidgets cities={data.cities} /> : <Error message="Please try to refresh." />}
      </section>
    </section>
  );
};

export default Dashboard;