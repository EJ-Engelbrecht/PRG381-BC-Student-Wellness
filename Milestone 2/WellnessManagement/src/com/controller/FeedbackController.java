package com.controller;

import com.dao.FeedbackDAOImpl;
import com.model.Feedback;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.dao.DBConnection.getConnection;

public class FeedbackController {

    private final FeedbackDAOImpl feedbackDAOImpl;

    public FeedbackController() {
        try {
            Connection conn = getConnection();
            this.feedbackDAOImpl = new FeedbackDAOImpl(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection failed.");
            throw new RuntimeException("Database connection failed: " + e.getMessage());
        }
    }

    public void submitFeedback(JTextField txtStudent, JComboBox<Integer> cbRating, JTextArea txtComments) {
        try {
            String student = txtStudent.getText().trim();
            int rating = (int) cbRating.getSelectedItem();
            String comments = txtComments.getText().trim();

            if (student.isEmpty() || comments.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            Feedback fb = new Feedback();
            
            fb.setStudent(student);
            fb.setRating(rating);
            fb.setComments(comments);
            
            feedbackDAOImpl.registerFeedback(fb);
            JOptionPane.showMessageDialog(null, "Feedback submitted!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error submitting feedback: \n" + e.getMessage());
        }
    }

    public void populateTable(JTable table) {
        try {
            List<Feedback> feedbackList = feedbackDAOImpl.getFeedback();
            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Student", "Rating", "Comments"}, 0);

            for (Feedback fb : feedbackList) {
                model.addRow(new Object[]{fb.getId(), fb.getStudent(), fb.getRating(), fb.getComments()});
            }

            table.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading feedback: \n" + e.getMessage());
        }
    }

    public void deleteFeedback(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this feedback?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int feedbackId = (int) table.getValueAt(selectedRow, 0);
            feedbackDAOImpl.deleteFeedback(feedbackId);
            
            boolean success = true;
            if (success) {
                JOptionPane.showMessageDialog(null, "Feedback deleted successfully.");
                ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to delete feedback.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error deleting feedback: " + e.getMessage());
        }
    }

    public void editFeedback(int id, String student, int rating, String comments) {
        try {
            Feedback updated = new Feedback(id, student, rating, comments);
            feedbackDAOImpl.updateFeedback(updated);
            
            boolean success = true;
            
         
            if (success) {
                JOptionPane.showMessageDialog(null, "Feedback updated successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update feedback.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating feedback: " + e.getMessage());
        }
    }
}
