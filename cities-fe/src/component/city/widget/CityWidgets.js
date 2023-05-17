import CityWidget from 'component/city/widget/CityWidget';

const CityWidgets = ({cities}) => {
  return cities?.length > 0 ? cities.map(city => {
    return <CityWidget key={city.id} city={city} />;
  }) : <div className="no-cities">No cities to display.</div>;
};

export default CityWidgets;