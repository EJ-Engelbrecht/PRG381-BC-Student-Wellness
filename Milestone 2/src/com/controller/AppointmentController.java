package com.controller;

import com.dao.AppointmentDAO;
import com.model.Appointment;

import javax.swing.*;

public class AppointmentController {

    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public boolean bookAppointment(Appointment a) {
        if (appointmentDAO.hasConflict(a.getCounselor(), a.getDate(), a.getTime())) {
            JOptionPane.showMessageDialog(null, "This time slot is already booked for the counselor.");
            return false;
        }

        // Proceed to save the appointment
        return appointmentDAO.addAppointment(a);  // assuming this method exists
    }
}
