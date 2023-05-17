const CityInformation = ({name, photo}) => {

  return (
    <div className="cityPhoto">
      <div id="cityName">{name}</div>
      <img src={photo} alt="" />
    </div>
  )

};

export default CityInformation;