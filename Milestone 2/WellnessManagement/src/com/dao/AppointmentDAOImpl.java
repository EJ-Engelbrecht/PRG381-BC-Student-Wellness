package com.dao;

import com.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.LocalTime;
import java.sql.Time;
import java.sql.Statement;

public class AppointmentDAOImpl implements AppointmentDAO {

    private Connection conn;

    public AppointmentDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public List<Appointment> getUpcomingAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointments WHERE date >= CURRENT_DATE ORDER BY date, time";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Appointment a = new Appointment();
                a.setId(rs.getInt("id"));
                a.setStudent(rs.getString("student"));
                a.setCounselor(rs.getString("counselor"));
                a.setDate(rs.getDate("date"));
                a.setTime(rs.getTime("time"));
                a.setStatus(rs.getString("status"));
                appointments.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    //retrieves appointment record list from database
    public List<Appointment> getAppointments() {
        String sql = "SELECT * FROM appointments";

        List<Appointment> appointments = new ArrayList<Appointment>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet result = stmt.executeQuery();

            //adds record details to "Appointment" object which is appended to a appointments object list
            while (result.next()) {
                Appointment ap = new Appointment();

                ap.setId(result.getInt("id"));
                ap.setStudent(result.getString("student"));
                ap.setCounselor(result.getString("counselor"));
                ap.setDate(result.getDate("date"));
                ap.setTime(result.getTime("time"));
                ap.setStatus(result.getString("status"));

                appointments.add(ap);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return appointments;
    }

    public List<Appointment> getAppointmentsByStudent(String name) {
        String sql = "SELECT * FROM appointments WHERE name = ?";

        List<Appointment> appointments = new ArrayList<Appointment>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            ResultSet result = stmt.executeQuery();

            //adds record details to "Appointment" object which is appended to a appointments object list
            while (result.next()) {
                Appointment ap = new Appointment();

                ap.setId(result.getInt("id"));
                ap.setStudent(result.getString("student"));
                ap.setCounselor(result.getString("counselor"));
                ap.setDate(result.getDate("date"));
                ap.setTime(result.getTime("time"));
                ap.setStatus(result.getString("status"));

                appointments.add(ap);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return appointments;
    }

    public boolean registerAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (student, counselor, date, time, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getStudent());
            stmt.setString(2, appointment.getCounselor());
            stmt.setDate(3, new java.sql.Date(appointment.getDate().getTime()));
            stmt.setTime(4, appointment.getTime());
            stmt.setString(5, appointment.getStatus());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Updates Appointment details based on "Student" Criteria
    public boolean updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET student = ?, counselor = ?, date = ?, time = ?, status = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getStudent());
            stmt.setString(2, appointment.getCounselor());
            stmt.setDate(3, new java.sql.Date(appointment.getDate().getTime()));
            stmt.setTime(4, appointment.getTime());
            stmt.setString(5, appointment.getStatus());
            stmt.setInt(6, appointment.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();  // or log it
            return false;
        }
    }

    public boolean hasTimeConflict(int id, String date, String time, String counselor) {
        String sql = """
        SELECT * FROM appointments
        WHERE date = ? AND counselor = ? AND id != ?
    """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date);
            stmt.setString(2, counselor);
            stmt.setInt(3, id);

            ResultSet rs = stmt.executeQuery();

            // Convert current time string to minutes
            int newTimeMins = toMinutes(time);

            while (rs.next()) {
                String existingTime = rs.getString("time");
                int existingTimeMins = toMinutes(existingTime);

                if (Math.abs(existingTimeMins - newTimeMins) < 30) {
                    return true; // Conflict found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // No conflict
    }

    // Helper method to convert "HH:mm" to total minutes
    private int toMinutes(String time) {
        time = time.trim();
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0].trim()) * 60 + Integer.parseInt(parts[1].trim());
    }

    public Appointment getAppointmentById(int id) {
        Appointment appointment = null;
        String query = "SELECT * FROM appointments WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                appointment = new Appointment();
                appointment.setId(rs.getInt("id"));
                appointment.setStudent(rs.getString("student_name"));
                appointment.setCounselor(rs.getString("counselor_id"));
                appointment.setDate(rs.getDate("date"));
                appointment.setTime(rs.getTime("time"));
                appointment.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment;
    }

    public boolean updateAppointmentStatus(int appointmentId, String newStatus) {
        String sql = "UPDATE appointments SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, appointmentId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasConflict(String counselor, Date date, Time time) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE counselor = ?, date = ?, time = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, counselor);

            //Converts standard date format to sql date format
            java.util.Date utilDate = date;
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            stmt.setDate(2, sqlDate);
            stmt.setTime(3, time);

            ResultSet rs = stmt.executeQuery();

            int count;

            if (rs.next()) {
                count = rs.getInt(1);
            } else {
                return false;
            }

            if (count > 0) {
                return false;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCancelledAppointments() {
        String sql = "DELETE FROM Appointments WHERE status = 'Cancelled'";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
