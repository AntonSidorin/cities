import { Routes, Route } from 'react-router-dom'
import Layout from 'component/util/Layout'
import Welcome from 'component/Welcome'
import RequireAuth from 'component/auth/RequireAuth'
import Dashboard from 'component/dashboard/Dashboard';
import City from 'component/city/City';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        {/* public routes */}
        <Route index element={<Welcome />} />

        {/* protected routes */}
        <Route element={<RequireAuth />}>
          <Route path="dashboard" element={<Dashboard />} />
          <Route path="city/:cityId" element={<City />} />
        </Route>

      </Route>
    </Routes>
  )
}

export default App;
