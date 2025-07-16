package com.view;

import com.dao.DBConnection;
import java.sql.Connection;
import com.util.DBInitialization;
import javax.swing.*;
import com.view.AppointmentPanel;
import com.view.FeedbackPanel;
import com.view.CounselorPanel;
import java.sql.*;


public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        setTitle("BC Wellness Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Appointments", new AppointmentPanel()); 
        tabbedPane.addTab("Counselors", new CounselorPanel());
        tabbedPane.addTab("Feedback", new FeedbackPanel());        
        
        add(tabbedPane);
        setVisible(true);
    }

    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> new DashboardFrame());

    try (Connection conn = DBConnection.getConnection()) {
        if (conn != null) {
            System.out.println("✅ Connected to DB.");
            DBInitialization.setupDatabase(conn);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    // ✅ Shutdown Derby cleanly after setup
    try {
        DriverManager.getConnection("jdbc:derby:;shutdown=true");
    } catch (SQLException e) {
        if ("XJ015".equals(e.getSQLState())) {
            System.out.println("✅ Derby shut down successfully.");
        } else {
            e.printStackTrace();
        }
    }
}

    }



