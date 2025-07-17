package com.controller;

import com.dao.AppointmentDAOImpl;
import com.model.Appointment;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import static com.dao.DBConnection.getConnection;

public class AppointmentController {

    private AppointmentDAOImpl appointmentDAOImpl;

    public AppointmentController() {
        try {
            Connection conn = getConnection();
            this.appointmentDAOImpl = new AppointmentDAOImpl(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
            e.printStackTrace();
        }
    }
    

    public boolean bookAppointment(Appointment a) {
        if (appointmentDAOImpl.hasConflict(a.getCounselor(), a.getDate(), a.getTime())) {
            JOptionPane.showMessageDialog(null, "This time slot is already booked for the counselor.");
            return false;
        }
        appointmentDAOImpl.registerAppointment(a);
        return true;
    }



    public List<Appointment> getAppointmentsByStudent(String studentName) {
        return appointmentDAOImpl.getAppointmentsByStudent(studentName);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDAOImpl.getAppointments();
    }
        public List<Appointment> getUpcomingAppointments() {
        return appointmentDAOImpl.getUpcomingAppointments();
    }
    
    public DefaultTableModel createAppointmentTableModel(List<Appointment> appointments) {
        String[] columns = {"ID","Date", "Time","Student", "Counselor", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Appointment a : appointments) {
            model.addRow(new Object[]{
                a.getId(),
                a.getDate(),
                a.getTime(),
                a.getStudent(),
                a.getCounselor(),
                a.getStatus()
            });
        }
        return model;
    }

}

