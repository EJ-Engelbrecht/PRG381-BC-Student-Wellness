package com.dao;

import com.model.Appointment;
import main.bc.dao.DBConnection;

import java.sql.*;

public class AppointmentDAO {

    public boolean addAppointment(Appointment a) {
        String sql = "INSERT INTO appointments (student, counselor, date, time, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getStudent());
            stmt.setString(2, a.getCounselor());

            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(a.getDate().getTime());
            stmt.setDate(3, sqlDate);

            // Convert java.util.Date to java.sql.Time
            java.sql.Time sqlTime = new java.sql.Time(a.getTime().getTime());
            stmt.setTime(4, sqlTime);

            stmt.setString(5, a.getStatus());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasConflict(String counselor, java.util.Date date, java.util.Date time) {
        String query = "SELECT * FROM appointments WHERE counselor = ? AND date = ? AND time = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, counselor);
            stmt.setDate(2, new java.sql.Date(date.getTime()));
            stmt.setTime(3, new java.sql.Time(time.getTime()));

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // Conflict exists if result found

        } catch (SQLException e) {
            e.printStackTrace();
            return true;  // Assume conflict on error
        }
    }
}
