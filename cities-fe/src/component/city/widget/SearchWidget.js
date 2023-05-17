import { logOut } from 'store/auth/authSlice';

const SearchWidget = () => {

  return (
    <div onClick={() => {dispatch(logOut());}} className="cityWidget cityActionWidget">
      <span>Log Out</span>
    </div>
  );
};

export default SearchWidget;