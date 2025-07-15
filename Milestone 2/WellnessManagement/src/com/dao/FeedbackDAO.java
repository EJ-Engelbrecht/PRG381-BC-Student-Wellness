package com.dao;

import com.model.Feedback;
import java.sql.*;
import java.util.*;

public class FeedbackDAO {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/WellnessManagement";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "sFicA";

    public static void addFeedback(Feedback fb) {
        String sql = "INSERT INTO feedback (student, rating, comments) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fb.getStudent());
            ps.setInt(2, fb.getRating());
            ps.setString(3, fb.getComments());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Feedback> getAllFeedback() {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM feedback";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Feedback fb = new Feedback(
                    rs.getInt("id"),
                    rs.getString("student"),
                    rs.getInt("rating"),
                    rs.getString("comments")
                );
                list.add(fb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void updateFeedback(Feedback fb) {
        String sql = "UPDATE feedback SET student = ?, rating = ?, comments = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fb.getStudent());
            ps.setInt(2, fb.getRating());
            ps.setString(3, fb.getComments());
            ps.setInt(4, fb.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFeedback(int id) {
        String sql = "DELETE FROM feedback WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
