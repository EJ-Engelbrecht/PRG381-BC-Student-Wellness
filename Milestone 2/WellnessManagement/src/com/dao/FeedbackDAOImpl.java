package com.dao;

import com.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAOImpl implements FeedbackDAO {

    private Connection conn;

    public FeedbackDAOImpl(Connection conn) {
        this.conn = conn;
    }

    //retrieves records from Feedback table
    public List<Feedback> getAllFeedback() {
        List<Feedback> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM feedback"); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Feedback f = new Feedback();
                f.setId(rs.getInt("id"));
                f.setStudent(rs.getString("student"));
                f.setRating(rs.getInt("rating"));
                f.setComments(rs.getString("comments"));
                list.add(f);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //adds new record to feedback table
    public void registerFeedback(Feedback feedback) {
        //prevents sql injection
        String sql = "INSERT INTO feedback (student, rating, comments) VALUES(?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, feedback.getStudent());
            stmt.setInt(2, feedback.getRating());
            stmt.setString(3, feedback.getComments());

            stmt.executeUpdate();

            System.out.println("Feedback added");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feedback not added");
        }
    }

    //Updates record based on "student" criteria
    public void updateFeedback(Feedback feedback) {
        //prevents sql injection
        String sql = "UPDATE feedback SET rating = ?, comments = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, feedback.getRating());
            stmt.setString(2, feedback.getComments());

            stmt.setInt(3, feedback.getId());

            stmt.executeUpdate();

            System.out.println("Feedback Updated");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feedback not updated");
        }
    }

    //removes record based on id criteria
    public void deleteFeedback(int id) {
        //prevents sql injection
        String sql = "DELETE FROM feedback WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Feedback Removed");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Feedback not Removed");
        }
    }
}
