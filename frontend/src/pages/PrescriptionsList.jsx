import React, { useEffect, useState } from "react";
import axios from "../services/axiosPrivate";

const PrescriptionsList = () => {
  const [prescriptions, setPrescriptions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchPrescriptions = async () => {
      try {
        const res = await axios.get("/prescriptions/patient");
        setPrescriptions(res.data);
      } catch (err) {
        console.error("Failed to load prescriptions", err);
        setError("Could not load prescriptions. Please check your login or try again.");
      } finally {
        setLoading(false);
      }
    };

    fetchPrescriptions();
  }, []);

  return (
    <div className="container">
      <h2>Your Prescriptions</h2>

      {loading && <p>Loading prescriptions...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {!loading && prescriptions.length === 0 && <p>No prescriptions found.</p>}

      <ul>
        {prescriptions.map((p) => (
          <li key={p.id}>
            <strong>{new Date(p.createdAt).toLocaleString()}</strong>:<br />
            {p.content}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default PrescriptionsList;
