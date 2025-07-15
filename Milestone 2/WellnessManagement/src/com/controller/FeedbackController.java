package com.controller;

import com.dao.FeedbackDAO;
import com.model.Feedback;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FeedbackController {

    public static void submitFeedback(JTextField txtStudent, JComboBox<Integer> cbRating, JTextArea txtComments) {
        String student = txtStudent.getText().trim();
        int rating = (int) cbRating.getSelectedItem();
        String comments = txtComments.getText().trim();

        if (student.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student name is required.");
            return;
        }

        Feedback fb = new Feedback(student, rating, comments);
        FeedbackDAO.addFeedback(fb);
        JOptionPane.showMessageDialog(null, "Feedback submitted!");
    }

    public static void populateTable(JTable table) {
        List<Feedback> feedbackList = FeedbackDAO.getAllFeedback();
        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Student", "Rating", "Comments"}, 0);

        for (Feedback fb : feedbackList) {
            model.addRow(new Object[]{fb.getId(), fb.getStudent(), fb.getRating(), fb.getComments()});
        }

        table.setModel(model);
    }

    public static void deleteFeedback(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row to delete.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        FeedbackDAO.deleteFeedback(id);
        JOptionPane.showMessageDialog(null, "Feedback deleted.");
    }

    public static void editFeedback(JTable table, JTextField txtStudent, JComboBox<Integer> cbRating, JTextArea txtComments) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row to edit.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        String student = txtStudent.getText().trim();
        int rating = (int) cbRating.getSelectedItem();
        String comments = txtComments.getText().trim();

        if (student.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Student name is required.");
            return;
        }

        Feedback fb = new Feedback(id, student, rating, comments);
        FeedbackDAO.updateFeedback(fb);
        JOptionPane.showMessageDialog(null, "Feedback updated.");
    }
}
