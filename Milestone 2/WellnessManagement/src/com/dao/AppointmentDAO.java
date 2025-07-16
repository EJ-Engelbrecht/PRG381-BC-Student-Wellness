package com.dao;

import com.model.Appointment;
import java.util.List;
import java.util.Date;
import java.sql.Time;

//Methods to interact with the Appointments Table
public interface AppointmentDAO {
    List<Appointment> getAppointments();
    void registerAppointment(Appointment appointment);
    void updateAppointment(Appointment appointment);
    void deleteAppointment(int id);
    List<Appointment> getAppointmentsByStudent(String name);
    boolean hasConflict(String counselor, Date date, Time time);
}
