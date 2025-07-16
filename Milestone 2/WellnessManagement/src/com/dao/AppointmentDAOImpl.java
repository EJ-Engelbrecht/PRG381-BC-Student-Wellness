package com.dao;
import com.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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

    //Insert new "Appointment" record
    public void registerAppointment(Appointment appointment) {
        //prevents sql injection
        String sql = "INSERT INTO appointments (student, counselor, date, time, status) VALUES(?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, appointment.getStudent());
            stmt.setString(2, appointment.getCounselor());

            //Converts standard date format to sql date format
            java.util.Date utilDate = appointment.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            stmt.setDate(3, sqlDate);
            stmt.setTime(4, appointment.getTime());

            stmt.executeUpdate();

            System.out.println("Appointment added");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Appointment not added");
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


    //Removes appointments record based on "Student" criteria
    public void deleteAppointment(String student) {
        //prevents sql injection
        String sql = "DELETE FROM appointments WHERE student = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student);

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
            stmt.setDate(2, date);
            stmt.setTime(3, time);

            ResultSet result = stmt.executeQuery();

            ResultSet rs = stmt.executeQuery(query));

            int count;

            if (rs.next()) {
                count = rs.getInt(1);
            } else {
                return null;
            }

            if (count > 0) {
                return false;
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}