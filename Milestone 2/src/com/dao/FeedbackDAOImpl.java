package com.dao;

import com.model.Feedback;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAOImpl implements FeedbackDAO {
    private Connection conn;

    public FeedbackDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Feedback> getFeedback() {
        String sql = "SELECT * FROM feedback";
        List<Feedback> feedbackList = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Feedback fb = new Feedback();
                fb.setStudent(result.getString("student"));
                fb.setRating(result.getInt("rating"));
                fb.setComments(result.getString("comments")); // Note: if comments are semicolon-separated, you'd need to split
                feedbackList.add(fb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return feedbackList;
    }

    @Override
    public void registerFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback (student, rating, comments) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, feedback.getStudent());
            stmt.setInt(2, feedback.getRating());
            stmt.setString(3, String.join(";", feedback.getComments()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFeedback(Feedback feedback) {
        String sql = "UPDATE feedback SET rating=?, comments=? WHERE student=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feedback.getRating());
            stmt.setString(2, String.join(";", feedback.getComments()));
            stmt.setString(3, feedback.getStudent());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFeedback(String student) {
        String sql = "DELETE FROM feedback WHERE student=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
