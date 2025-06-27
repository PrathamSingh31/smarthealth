// src/services/axiosPrivate.js
import axios from "axios";

const axiosPrivate = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

axiosPrivate.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token"); // or however you store JWT
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default axiosPrivate;
