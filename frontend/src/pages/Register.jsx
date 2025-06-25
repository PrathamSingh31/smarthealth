import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    name: '',
    email: '',
    password: '',
    role: 'PATIENT' // default role
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/auth/register', form);
      alert('Registration successful! Please login.');
      navigate('/login');
    } catch (error) {
      alert('Registration failed: ' + (error.response?.data?.message || 'Server error'));
    }
  };

  return (
    <div style={{ maxWidth: '400px', margin: 'auto', paddingTop: '80px' }}>
      <h2>Register</h2>
      <form onSubmit={handleRegister}>
        <input
          type="text"
          name="name"
          placeholder="Full Name"
          value={form.name}
          required
          onChange={handleChange}
          style={{ width: '100%', marginBottom: '10px' }}
        />
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={form.email}
          required
          onChange={handleChange}
          style={{ width: '100%', marginBottom: '10px' }}
        />
        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          required
          onChange={handleChange}
          style={{ width: '100%', marginBottom: '10px' }}
        />
        <select
          name="role"
          value={form.role}
          onChange={handleChange}
          style={{ width: '100%', marginBottom: '10px' }}
        >
          <option value="PATIENT">Patient</option>
          <option value="DOCTOR">Doctor</option>
          <option value="ADMIN">Admin</option>
        </select>
        <button type="submit" style={{ width: '100%' }}>
          Register
        </button>
      </form>
    </div>
  );
}

export default Register;
