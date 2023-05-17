import { useContext, useEffect, useRef, useState } from 'react';
import { CollapsibleActionContext } from 'component/city/action/CollapsibleAction';
import { useUpdateCityMutation } from 'api/city/cityApiSlice';
import Loading from 'component//util/Loading';

const UpdateCityInformationAction = ({city}) => {

  const setErrMsg = useContext(CollapsibleActionContext);

  const nameRef = useRef();

  const [name, setName] = useState(city.name);
  const [photo, setPhoto] = useState(city.photo);
  const [updateCity, { isLoading }] = useUpdateCityMutation();

  useEffect(() => {
    nameRef.current.focus();
  }, []);

  useEffect(() => {
    setErrMsg('');
  }, [name, photo, setErrMsg]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await updateCity({ id: city.id, name, photo }).unwrap();
      nameRef.current.focus();
    } catch (error) {
      if (!error?.status) {
        // isLoading: true until timeout occurs
        setErrMsg(['No Server Response']);
      } else if (error?.data?.messages) {
        setErrMsg(error.data.messages);
      } else if (error.status === 400) {
        setErrMsg(['Incorrect City Name or Photo']);
      } else if (error.status === 401) {
        setErrMsg(['Unauthorized']);
      } else {
        setErrMsg('City Update Failed');
      }
    }
  };

  const handleCityNameInput = (e) => {
    setName(e.target.value);
  };

  const handleCityPhotoInput = (e) => {
    setPhoto(e.target.value);
  };

  if (isLoading) return <Loading />;

  return (
    <div id="city_information">
      <form onSubmit={handleSubmit}>
        <label htmlFor="city_name">City name:</label>
        <input
          ref={nameRef}
          type="text"
          id="city_name"
          placeholder="City name"
          maxLength="200"
          onChange={handleCityNameInput}
          value={name}
          autoComplete="off"
          required
        />
        <label htmlFor="city_photo">City photo:</label>
        <input
          type="text"
          id="city_photo"
          placeholder="City photo url"
          maxLength="1000"
          onChange={handleCityPhotoInput}
          value={photo}
          autoComplete="off"
          required
        />
        <button>Update</button>
      </form>
    </div>
  );
};

export default UpdateCityInformationAction;
