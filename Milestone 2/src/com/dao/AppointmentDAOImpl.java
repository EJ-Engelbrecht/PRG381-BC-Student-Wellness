package com.dao;

import com.model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAOImpl implements AppointmentDAO {
    private Connection conn;

    public AppointmentDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Appointment> getAppointments() {
        String sql = "SELECT * FROM appointments";
        List<Appointment> appointments = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Appointment appt = new Appointment();
                appt.setStudent(result.getString("student"));
                appt.setCounselor(result.getString("counselor"));
                appt.setDate(result.getDate("date"));
                appt.setTime(result.getTime("time"));
                appt.setStatus(result.getString("status"));
                appointments.add(appt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return appointments;
    }

    @Override
    public void registerAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (student, counselor, date, time, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getStudent());
            stmt.setString(2, appointment.getCounselor());
            stmt.setDate(3, new java.sql.Date(appointment.getDate().getTime()));
            stmt.setTime(4, appointment.getTime());
            stmt.setString(5, appointment.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET counselor=?, date=?, time=?, status=? WHERE student=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getCounselor());
            stmt.setDate(2, new java.sql.Date(appointment.getDate().getTime()));
            stmt.setTime(3, appointment.getTime());
            stmt.setString(4, appointment.getStatus());
            stmt.setString(5, appointment.getStudent());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAppointment(String student) {
        String sql = "DELETE FROM appointments WHERE student=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasConflict(String counselor, java.util.Date date, java.sql.Time time) {
        String sql = "SELECT 1 FROM appointments WHERE counselor = ? AND date = ? AND time = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, counselor);
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            stmt.setTime(3, time);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // assume conflict if error
        }
    }

    @Override
    public boolean addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (student, counselor, date, time, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getStudent());
            stmt.setString(2, appointment.getCounselor());
            stmt.setDate(3, new java.sql.Date(appointment.getDate().getTime()));
            stmt.setTime(4, appointment.getTime());
            stmt.setString(5, appointment.getStatus());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
