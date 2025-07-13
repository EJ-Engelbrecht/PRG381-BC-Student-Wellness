package com.controller;

import com.model.Appointment;
import java.util.Date;
import java.util.Calendar;

public class TestBooking {
    public static void main(String[] args) {
        // Create a fake appointment
        Appointment appointment = new Appointment();
        appointment.setStudent("s12345");
        appointment.setCounselor("Dr. Smith");

        // Set date and time
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.JULY, 18, 11, 30); // 18 July 2025, 10:30
        appointment.setDate(new java.sql.Time(cal.getTimeInMillis()));
        appointment.setTime(new java.sql.Time(cal.getTimeInMillis()));

        appointment.setStatus("Scheduled");

        // Try booking
        AppointmentController controller = new AppointmentController();
        boolean success = controller.bookAppointment(appointment);

        if (success) {
            System.out.println("✅ Appointment booked.");
        } else {
            System.out.println("❌ Booking failed (possibly conflict).");
        }
    }
}
