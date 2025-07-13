package com.controller;

import javax.swing.*;
import com.model.Feedback;

public class FeedbackController {

    public boolean validateFeedback(Feedback f) {
        if (f.getRating() < 1 || f.getRating() > 5) {
            JOptionPane.showMessageDialog(null, "Rating must be between 1 and 5.");
            return false;
        }
        if (f.getComments().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Comment cannot be empty.");
            return false;
        }
        return true;
    }

}
