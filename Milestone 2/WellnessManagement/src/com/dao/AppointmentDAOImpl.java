package com.dao;
import com.model.;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AppointmentDAOImpl implements AppointmentDAO {
    private Connection conn;

    public AppointmentDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public List<Appointment> getAppointments() {
        String sql = "SELECT * FROM appointments";

        List<Appointment> appointments = new ArrayList<Appointment>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet result = stmt.executeQuery();

            while (result.next()){
                Appointment ap = new Appointment();

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

    public void registerAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (student, counselor, date, time, status) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getStudent());
            stmt.setString(2, appointment.getCounselor());
            stmt.setDate(3, appointment.getDate());
            stmt.setTime(4, appointment.getTime());

            stmt.executeUpdate();

            System.out.println("Appointment added");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Appointment not added");
        }
    }

    public void updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET counselor = ?, date = ?, time = ?, status = ? WHERE student = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getCounselor());
            stmt.setDate(2, appointment.getDate());
            stmt.setTime(3, appointment.getTime());
            stmt.setString(4, appointment.getStudent());

            stmt.executeUpdate();

            System.out.println("Appointment Updated");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Appointment not updated");
        }
    }

    public void deleteAppointment(String student) {
        String sql = "DELETE FROM appointments WHERE student = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student);

            stmt.executeUpdate();

            System.out.println("Appointment Removed");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Appointment not Removed");
        }
    }
}