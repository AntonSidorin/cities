import { Link, useParams } from 'react-router-dom';
import { useGetCityQuery } from 'api/city/cityApiSlice';
import Loading from 'component/util/Loading';
import Error from 'component/error/Error';
import UpdateCityInformationAction from 'component/city/action/UpdateCityInformationAction';
import CollapsibleAction from 'component/city/action/CollapsibleAction';
import CityInformation from 'component/city/information/CityInformation';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from 'store/auth/authSlice';

const City = () => {

  const { cityId } = useParams();

  const user = useSelector(selectCurrentUser);

  const {
    data: city,
    isLoading,
    isError,
  } = useGetCityQuery(Number(cityId));

  if (isLoading) return <Loading />;

  if (isError) return (
    <Error to="/dashboard" name="dashboard" message="Please try to open wallet from " />
  );

  return (
    <section>
      <section>
        <Link to="/dashboard">Back</Link>
      </section>

      <CityInformation name={city.name} photo={city.photo} />
      {user.allowEdit && <CollapsibleAction label="Update City Information">
        <UpdateCityInformationAction city={city} />
      </CollapsibleAction>}

    </section>
  );

};

export default City;
