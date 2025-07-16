package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.*;

public class CounselorDAOImpl implements CounselorDAO {
    private Connection conn;

    public CounselorDAOImpl(Connection conn) {
        this.conn = conn;
    }

    //retrieves records from the "counselors" table
    public List<Counselor> getCounselors() {
        String sql = "SELECT * FROM counselors";

        List<Counselor> Counselors = new ArrayList<Counselor>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet result = stmt.executeQuery();

            //creates, with each record, a counselor object with record details added
            while (result.next()){
                Counselor cs = new Counselor();

                cs.setName(result.getString("name"));
                cs.setSpecialization(result.getString("specialization"));
                cs.setAvailability(result.getBoolean("availability"));

                //appends to List
                Counselors.add(cs);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return Counselors;
    }

    //adds new record to counselor table
    public void registerCounselor(Counselor counselor) {
        //prevents sql injection
        String sql = "INSERT INTO counselors (name, specialization, availability) VALUES(?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, counselor.getName());
            stmt.setString(2, counselor.getSpecialization());
            stmt.setBoolean(3, counselor.isAvailable());

            stmt.executeUpdate();

            System.out.println("Counselor added");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Counselor not added");
        }
    }

    //updates record based on "name" criteria
    public void updateCounselor(Counselor counselor) {
        //prevents sql injection
        String sql = "UPDATE counselors SET specialization = ?, availability = ? WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, counselor.getSpecialization());
            stmt.setBoolean(2, counselor.isAvailable());
            stmt.setString(3, counselor.getName());

            stmt.executeUpdate();

            System.out.println("Counselor Updated");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Counselor not updated");
        }
    }

    //removes record based on "id" criteria
    public void deleteCounselor(int id) {
        //prevents sql injection
        String sql = "DELETE FROM counselors WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();

            System.out.println("Counselor Removed");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Counselor not Removed");
        }
    }
}