package com.dao;
import com.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class FeedbackDAOImpl implements FeedbackDAO {
    private Connection conn;

    public FeedbackDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Feedback> getFeedback() {
        String sql = "SELECT * FROM feedback";

        ArrayList<Feedback> FeedbackList = new ArrayList<Feedback>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet result = stmt.executeQuery();

            while (result.next()){
                Feedback fb = new Feedback();

                fb.setStudent(result.getString("student"));
                fb.setRating(result.getInt("rating"));
                fb.setComments(result.getArray("comments"));

                FeedbackList.add(fb);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return FeedbackList;
    }

    public void registerFeedback(Feedback feedback) {
        String sql = "INSERT INTO feedback (student, rating, comments) VALUES(?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, feedback.getStudent());
            stmt.setInt(2, feedback.getRating());
            stmt.setArray(3, feedback.getComments());

            stmt.executeUpdate();

            System.out.println("Feedback added");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feedback not added");
        }
    }

    public void updateFeedback(Feedback feedback) {
        String sql = "UPDATE feedback SET rating = ?, comments = ? WHERE student = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feedback.getRating());
            stmt.setArray(2, feedback.getComments());
            stmt.setString(3, feedback.getStudent());

            stmt.executeUpdate();

            System.out.println("Feedback Updated");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feedback not updated");
        }
    }

    public void deleteFeedback(String student) {
        String sql = "DELETE FROM feedback WHERE student = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student);

            stmt.executeUpdate();

            System.out.println("Feedback Removed");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feedback not Removed");
        }
    }
}