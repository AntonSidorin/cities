import { useNavigate } from 'react-router-dom';

const CityWidget = ({ city }) => {

  const navigate = useNavigate();

  return (
    <div onClick={() => navigate(`/city/${city.id}`)} className="cityWidget" key={city.id}>
      <span>{city.name}</span>
    </div>
  );

};

export default CityWidget;