package com.dao;

import com.model.Appointment;
import java.util.List;

// Methods to interact with the Appointments Table
public interface AppointmentDAO {
    List<Appointment> getAppointments();
    void registerAppointment(Appointment appointment);
    void updateAppointment(Appointment appointment);
    void deleteAppointment(String student);
    boolean hasConflict(String counselor, java.util.Date date, java.sql.Time time);
    boolean addAppointment(Appointment appointment);

}
