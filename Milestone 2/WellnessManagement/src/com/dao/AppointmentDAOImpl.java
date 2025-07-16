package com.dao;
import com.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.LocalTime;
import java.sql.Time;


public class AppointmentDAOImpl implements AppointmentDAO {
    private Connection conn;

    public AppointmentDAOImpl(Connection conn) {
        this.conn = conn;
    }

    //retrieves appointment record list from database
    public List<Appointment> getAppointments() {
        String sql = "SELECT * FROM appointments";

        List<Appointment> appointments = new ArrayList<Appointment>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet result = stmt.executeQuery();

            //adds record details to "Appointment" object which is appended to a appointments object list
            while (result.next()){
                Appointment ap = new Appointment();

                ap.setId(result.getInt("id"));
                ap.setStudent(result.getString("student"));
                ap.setCounselor(result.getString("counselor"));
                ap.setDate(result.getDate("date"));
                ap.setTime(result.getTime("time"));
                ap.setStatus(result.getString("status"));

                appointments.add(ap);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return appointments;
    }
    
    public List<Appointment> getAppointmentsByStudent(String name) {
        String sql = "SELECT * FROM appointments WHERE name = ?";

        List<Appointment> appointments = new ArrayList<Appointment>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            
            ResultSet result = stmt.executeQuery();

            //adds record details to "Appointment" object which is appended to a appointments object list
            while (result.next()){
                Appointment ap = new Appointment();
                
                ap.setId(result.getInt("id"));
                ap.setStudent(result.getString("student"));
                ap.setCounselor(result.getString("counselor"));
                ap.setDate(result.getDate("date"));
                ap.setTime(result.getTime("time"));
                ap.setStatus(result.getString("status"));

                appointments.add(ap);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return appointments;
    }

    public boolean registerAppointment(Appointment appointment) {
    String sql = "INSERT INTO appointments (student, counselor, date, time, status) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, appointment.getStudent());
        stmt.setString(2, appointment.getCounselor());
        stmt.setDate(3, new java.sql.Date(appointment.getDate().getTime()));
        stmt.setTime(4, appointment.getTime());
        stmt.setString(5, appointment.getStatus());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    //Updates Appointment details based on "Student" Criteria
    public void updateAppointment(Appointment appointment) {
        //prevents sql injection
        String sql = "UPDATE appointments SET counselor = ?, date = ?, time = ?, status = ? WHERE student = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getCounselor());

            //Converts standard date format to sql date format
            java.util.Date utilDate = appointment.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            stmt.setDate(2, sqlDate);
            stmt.setTime(3, appointment.getTime());
            stmt.setString(4, appointment.getStudent());

            stmt.executeUpdate();

            System.out.println("Appointment Updated");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Appointment not updated");
        }
    }


    //Removes appointments record based on "id" criteria
    public void deleteAppointment(int id) {
        //prevents sql injection
        String sql = "DELETE FROM appointments WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Appointment Removed");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Appointment not Removed");
        }
    }

    public boolean hasConflict(String counselor, Date date, Time time) {
        String sql = "SELECT COUNT(*) FROM appointments WHERE counselor = ?, date = ?, time = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, counselor);
            
            //Converts standard date format to sql date format
            java.util.Date utilDate = date;
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            
            stmt.setDate(2, sqlDate);
            stmt.setTime(3, time);

            ResultSet rs = stmt.executeQuery();

            int count;

            if (rs.next()) {
                count = rs.getInt(1);
            } else {
                return false;
            }

            if (count > 0) {
                return false;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
