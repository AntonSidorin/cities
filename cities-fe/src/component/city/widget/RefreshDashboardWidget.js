const RefreshDashboardWidget = ({refetch}) => {
  return (
    <div onClick={refetch} className="cityWidget cityActionWidget">
      <span>Refresh</span>
    </div>
  );
};

export default RefreshDashboardWidget;