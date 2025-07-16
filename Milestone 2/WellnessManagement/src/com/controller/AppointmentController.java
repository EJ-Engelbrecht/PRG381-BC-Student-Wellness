package com.controller;

import com.dao.AppointmentDAOImpl;
import com.model.Appointment;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.dao.DBConnection.getConnection;

public class AppointmentController {

    private final AppointmentDAOImpl appointmentDAOImpl;

    public AppointmentController() {
        try {
            Connection conn = getConnection();
            this.appointmentDAOImpl = new AppointmentDAOImpl(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to DB", e);
        }
    }

    public boolean bookAppointment(Appointment a) {
        if (appointmentDAOImpl.hasConflict(a.getCounselor(), a.getDate(), a.getTime())) {
            JOptionPane.showMessageDialog(null, "This time slot is already booked for the counselor.");
            return false;
        }

        return appointmentDAOImpl.registerAppointment(a);
    }

    public boolean cancelAppointment(int appointmentId) {
        return appointmentDAOImpl.cancelAppointment(appointmentId);
    }

    public List<Appointment> getAppointmentsByStudent(String studentName) {
        return appointmentDAOImpl.getAppointmentsByStudent(studentName);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDAOImpl.getAllAppointments();
    }
}
