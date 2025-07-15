package com.dao;

import com.model.*;
import java.util.List;

//Methods to interact with the Appointments Table
interface AppointmentDAO {
    List<Appointment> getAppointments();
    void registerAppointment(Appointment appointment);
    void updateAppointment(Appointment appointment);
    void deleteAppointment(String student);
}