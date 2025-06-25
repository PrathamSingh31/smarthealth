// src/pages/Appointments.jsx
import React, { useEffect, useState } from 'react';
import axios from '../services/axiosPrivate';
import { getUserFromToken } from '../utils/jwtUtils';

const Appointments = () => {
  const [appointments, setAppointments] = useState([]);
  const [role, setRole] = useState('');

  useEffect(() => {
    const fetchAppointments = async () => {
      try {
        const user = getUserFromToken(); // Get role from token
        setRole(user.role);

        const endpoint =
          user.role === 'DOCTOR'
            ? '/api/appointments/doctor'
            : '/appointments/patient';

        const response = await axios.get(endpoint);
        setAppointments(response.data);
      } catch (error) {
        console.error('Failed to fetch appointments:', error);
      }
    };

    fetchAppointments();
  }, []);

  return (
    <div className="container mt-4">
      <h2>{role === 'DOCTOR' ? 'Doctor' : 'Patient'} Appointments</h2>
      {appointments.length === 0 ? (
        <p>No appointments found.</p>
      ) : (
        <table className="table table-bordered mt-3">
          <thead>
            <tr>
              <th>ID</th>
              <th>Date & Time</th>
              <th>Doctor</th>
              <th>Patient</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {appointments.map((appt) => (
              <tr key={appt.id}>
                <td>{appt.id}</td>
                <td>{appt.appointmentDateTime}</td>
                <td>{appt.doctor?.name || '-'}</td>
                <td>{appt.patient?.name || '-'}</td>
                <td>{appt.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default Appointments;
