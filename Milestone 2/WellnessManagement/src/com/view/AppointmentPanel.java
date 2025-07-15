package com.view;

import javax.swing.*;
import java.awt.*;

public class AppointmentPanel extends JPanel {

    public JTextField txtStudentName, txtDate;
    public JComboBox<String> cbCounselor, cbTime, cbStatus;
    public JButton btnBook, btnUpdate, btnCancel, btnViewAll;
    public JTable tblAppointments;

    public AppointmentPanel() {
        setLayout(new BorderLayout());

        // --- Form Panel ---
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        txtStudentName = new JTextField();
        txtDate = new JTextField();

        cbCounselor = new JComboBox<>();
        cbTime = new JComboBox<>(new String[]{
                "08:00", "09:00", "10:00", "11:00", "12:00",
                "13:00", "14:00", "15:00", "16:00"
        });
        cbStatus = new JComboBox<>(new String[]{
                "Scheduled", "Completed", "Cancelled"
        });

        formPanel.add(new JLabel("Student Name:"));
        formPanel.add(txtStudentName);
        formPanel.add(new JLabel("Counselor:"));
        formPanel.add(cbCounselor);
        formPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        formPanel.add(txtDate);
        formPanel.add(new JLabel("Time:"));
        formPanel.add(cbTime);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(cbStatus);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnBook = new JButton("Book");
        btnUpdate = new JButton("Update");
        btnCancel = new JButton("Cancel");
        btnViewAll = new JButton("View All");

        buttonPanel.add(btnBook);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnViewAll);

        // --- Table ---
        tblAppointments = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblAppointments);

        // --- Add to Main Layout ---
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }
}
