package com.controller;

import javax.swing.*;
import com.model.Feedback;

public class FeedbackController {

    public boolean validateFeedback(Feedback f) {
        if (f.getRating() < 1 || f.getRating() > 5) {
            JOptionPane.showMessageDialog(null, "Rating must be between 1 and 5.");
            return false;
        }

        // Check if there are any comments and if at least one is not empty after trimming
        boolean hasValidComment = f.getComments().stream()
                .anyMatch(comment -> !comment.trim().isEmpty());

        if (!hasValidComment) {
            JOptionPane.showMessageDialog(null, "At least one non-empty comment is required.");
            return false;
        }

        return true;
    }
}
