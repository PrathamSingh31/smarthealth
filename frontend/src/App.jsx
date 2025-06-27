import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Login from './pages/Login.jsx';
import Register from './pages/Register.jsx';
import Dashboard from './pages/Dashboard.jsx';
import ProtectedRoute from './components/ProtectedRoute.jsx';
import BookAppointment from "./pages/BookAppointment";
import Appointments from './pages/Appointments';
import AddPrescription from "./pages/AddPrescription";
import PrescriptionsList from "./pages/PrescriptionsList";
function App() {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route
        path="/dashboard"
        element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        }
      />
      <Route path="/create-prescription" element={<AddPrescription />} />
      <Route path="/book" element={<BookAppointment />} />
      <Route path="/appointments" element={<Appointments />} />
        <Route path="/prescriptions" element={<PrescriptionsList />} />
    </Routes>
  );
}

export default App;
