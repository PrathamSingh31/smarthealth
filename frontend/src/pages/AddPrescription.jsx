// src/pages/AddPrescription.jsx
import React, { useEffect, useState } from "react";
import axios from "../services/axiosPrivate";
import { useNavigate } from "react-router-dom";

const AddPrescription = () => {
  const [appointments, setAppointments] = useState([]);
  const [selectedAppointmentId, setSelectedAppointmentId] = useState("");
  const [content, setContent] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchAppointments = async () => {
      try {
        const res = await axios.get("/appointments/doctor");
        setAppointments(res.data);
      } catch (error) {
        console.error("Error fetching appointments", error);
        setError("Failed to load appointments.");
      }
    };

    fetchAppointments();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!selectedAppointmentId || !content) return;

    try {
      setLoading(true);
      await axios.post(
        `/prescriptions/create?appointmentId=${selectedAppointmentId}`,
        { content }
      );
      alert("Prescription added successfully");
      navigate("/prescriptions");
    } catch (error) {
      console.error("Error adding prescription", error);
      alert("Failed to add prescription");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h2>Add Prescription</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <form onSubmit={handleSubmit}>
        <div>
          <label>Select Appointment:</label>
          <select
            value={selectedAppointmentId}
            onChange={(e) => setSelectedAppointmentId(e.target.value)}
            required
          >
            <option value="">-- Choose --</option>
            {appointments.map((a) => (
              <option key={a.id} value={a.id}>
                {a.patient.name} on {a.appointmentDateTime}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label>Prescription Content:</label>
          <textarea
            rows={5}
            value={content}
            onChange={(e) => setContent(e.target.value)}
            required
          />
        </div>

        <button type="submit" disabled={loading}>
          {loading ? "Saving..." : "Save Prescription"}
        </button>
      </form>
    </div>
  );
};

export default AddPrescription;
