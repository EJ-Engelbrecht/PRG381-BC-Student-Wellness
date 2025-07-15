package com.view;

import javax.swing.*;
import java.awt.*;

public class CounselorPanel extends JPanel {

    public JTextField txtName, txtSpecialization;
    public JComboBox<String> cbAvailability;
    public JButton btnAdd, btnUpdate, btnDelete, btnViewAll;
    public JTable tblCounselors;

    public CounselorPanel() {
        setLayout(new BorderLayout());

        // --- Form Panel ---
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        txtName = new JTextField();
        txtSpecialization = new JTextField();
        cbAvailability = new JComboBox<>(new String[] {
            "Available", "Unavailable"
        });

        formPanel.add(new JLabel("Name:"));
        formPanel.add(txtName);
        formPanel.add(new JLabel("Specialization:"));
        formPanel.add(txtSpecialization);
        formPanel.add(new JLabel("Availability:"));
        formPanel.add(cbAvailability);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnViewAll = new JButton("View All");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewAll);

        // --- Table Panel ---
        tblCounselors = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblCounselors);

        // --- Add to Main Layout ---
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
    }
}
