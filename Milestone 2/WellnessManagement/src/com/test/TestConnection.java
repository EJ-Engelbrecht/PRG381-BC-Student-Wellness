package com.test;

import com.dao.AppointmentDAO;
import com.dao.AppointmentDAOImpl;
import com.dao.DBConnection;
import com.model.Appointment;

import java.sql.Connection;
import java.util.List;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ Failed to connect to database.");
                return;
            }

            AppointmentDAO dao = new AppointmentDAOImpl(conn);
            List<Appointment> appointments = dao.getAppointments();

            if (appointments == null || appointments.isEmpty()) {
                System.out.println("📭 No appointments found.");
            } else {
                System.out.println("📅 Appointments:");
                for (Appointment a : appointments) {
                    System.out.println("----------------------------");
                    System.out.println("ID:       " + a.getId());
                    System.out.println("Student:  " + a.getStudent());
                    System.out.println("Counselor:" + a.getCounselor());
                    System.out.println("Date:     " + a.getDate());
                    System.out.println("Time:     " + a.getTime());
                    System.out.println("Status:   " + a.getStatus());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
