package com.view;

import javax.swing.*;
import com.view.AppointmentPanel;
import com.view.FeedbackPanel;
import com.view.CounselorPanel;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("BC Wellness Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Appointments", new AppointmentPanel()); // Example
        tabbedPane.addTab("Counselors", new CounselorPanel());
        tabbedPane.addTab("Feedback", new FeedbackPanel());        
        
        add(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new DashboardFrame());
    }


}
