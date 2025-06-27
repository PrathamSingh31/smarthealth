import React from 'react';
import { Link } from 'react-router-dom';

const Dashboard = () => {
  return (
    <div>
      <h2>Welcome to SmartHealth Dashboard</h2>
      <Link to="/create-prescription">Add Prescription</Link>
    </div>
  );
};

export default Dashboard;
