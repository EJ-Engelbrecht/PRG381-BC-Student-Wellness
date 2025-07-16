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

    //retrieves records from Feedback table
    public ArrayList<Feedback> getFeedback() {
        String sql = "SELECT * FROM feedback";

        ArrayList<Feedback> FeedbackList = new ArrayList<Feedback>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet result = stmt.executeQuery();

            //adds record details to feedback object which is then appended to a details object ArrayList
            while (result.next()){
                Feedback fb = new Feedback();

                fb.setId(result.getInt("id"));
                fb.setStudent(result.getString("student"));
                fb.setRating(result.getInt("rating"));

                //Converts sql Array format to standard Array format
                java.sql.Array sqlArray = result.getArray("comments");
                String[] comments = (String[]) sqlArray.getArray();
                fb.setComments(comments);

                FeedbackList.add(fb);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return FeedbackList;
    }

    //adds new record to feedback table
    public void registerFeedback(Feedback feedback) {
        //prevents sql injection
        String sql = "INSERT INTO feedback (student, rating, comments) VALUES(?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, feedback.getStudent());
            stmt.setInt(2, feedback.getRating());

            //converts standard array format to sql array format
            java.sql.Array sqlArray = conn.createArrayOf("VARCHAR", feedback.getComments());
            stmt.setArray(3, sqlArray);

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

            //converts standard array format to sql array format
            java.sql.Array sqlArray = conn.createArrayOf("VARCHAR", feedback.getComments());
            stmt.setArray(2, sqlArray);

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