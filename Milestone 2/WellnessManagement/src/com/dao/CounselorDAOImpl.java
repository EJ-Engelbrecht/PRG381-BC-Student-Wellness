package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.*;
import java.sql.Statement;

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
                cs.setId(result.getInt("id"));
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

    public void registerCounselor(Counselor counselor) {
    String sql = "INSERT INTO counselors (name, specialization, availability) VALUES (?, ?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        stmt.setString(1, counselor.getName());
        stmt.setString(2, counselor.getSpecialization());
        stmt.setBoolean(3, counselor.isAvailable());

        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating counselor failed, no rows affected.");
        }

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                counselor.setId(generatedKeys.getInt(1)); // Set the generated ID on the object
                System.out.println("Counselor added with ID: " + counselor.getId());
            } else {
                throw new SQLException("Creating counselor failed, no ID obtained.");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Counselor not added.");
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
    
    public boolean addCounselor(Counselor counselor) {
        return true;
    }
    
    public List<Counselor> getAvailableCounselors() {
    List<Counselor> list = new ArrayList<>();
    String sql = "SELECT id, name, specialization, availability FROM counselors WHERE availability = TRUE ORDER BY specialization ASC, name ASC";

    try (PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Counselor counselor = new Counselor();
            counselor.setId(rs.getInt("id"));
            counselor.setName(rs.getString("name"));
            counselor.setSpecialization(rs.getString("specialization"));
            counselor.setAvailability(rs.getBoolean("availability"));
            list.add(counselor);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}


}
