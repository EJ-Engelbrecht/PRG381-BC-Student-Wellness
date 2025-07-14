package com.controller;

import com.dao.AppointmentDAO;
import com.dao.AppointmentDAOImpl;
import com.model.Appointment;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

import static com.dao.DBConnection.getConnection;

public class AppointmentController {

    private final AppointmentDAO appointmentDAO;

    public AppointmentController() {
        Connection conn;
        try {
            conn = getConnection();
            this.appointmentDAO = new AppointmentDAOImpl(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to DB", e);
        }
    }

    public boolean bookAppointment(Appointment a) {
        if (appointmentDAO.hasConflict(a.getCounselor(), a.getDate(), a.getTime())) {
            JOptionPane.showMessageDialog(null, "This time slot is already booked for the counselor.");
            return false;
        }

        return appointmentDAO.addAppointment(a);
    }
}
