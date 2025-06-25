import React, { useEffect, useState } from "react";
import axios from "../services/axiosPrivate";
import { useNavigate } from "react-router-dom";

function BookAppointment() {
  const [doctors, setDoctors] = useState([]);
  const [selectedDoctorId, setSelectedDoctorId] = useState("");
  const [appointmentDateTime, setAppointmentDateTime] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch list of doctors
    const fetchDoctors = async () => {
      try {
        const res = await axios.get("/doctors");
        setDoctors(res.data);
      } catch (error) {
        console.error("Error fetching doctors", error);
      }
    };
    fetchDoctors();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!selectedDoctorId || !appointmentDateTime) return;

    try {
      await axios.post(`/appointments/book`, null, {
        params: {
          doctorId: selectedDoctorId,
          dateTime: appointmentDateTime,
        },
      });
      alert("Appointment booked successfully!");
      navigate("/appointments");
    } catch (error) {
      console.error("Booking failed", error);
      alert("Booking failed");
    }
  };

  return (
    <div className="container">
      <h2>Book Appointment</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Select Doctor:</label>
          <select
            value={selectedDoctorId}
            onChange={(e) => setSelectedDoctorId(e.target.value)}
          >
            <option value="">-- Choose --</option>
            {doctors.map((doc) => (
              <option key={doc.id} value={doc.id}>
                Dr. {doc.name} ({doc.specialization})
              </option>
            ))}
          </select>
        </div>

        <div>
          <label>Appointment Date & Time:</label>
          <input
            type="datetime-local"
            value={appointmentDateTime}
            onChange={(e) => setAppointmentDateTime(e.target.value)}
          />
        </div>

        <button type="submit">Book Appointment</button>
      </form>
    </div>
  );
}

export default BookAppointment;
