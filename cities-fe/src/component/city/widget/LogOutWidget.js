import { useDispatch } from 'react-redux';
import { logOut } from 'store/auth/authSlice';
import { clearFilter } from 'store/filter/filterSlice';

const LogOutWidget = () => {

  const dispatch = useDispatch();

  return (
    <div onClick={() => {dispatch(clearFilter()); dispatch(logOut());}} className="cityWidget cityActionWidget">
      <span>Log Out</span>
    </div>
  );
};

export default LogOutWidget;