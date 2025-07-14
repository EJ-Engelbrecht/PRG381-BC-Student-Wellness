package com.dao;

import com.model.Appointment;
import com.model.Counselor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class CounselorDAOImpl {
    private Connection conn;

    public CounselorDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public List<Counselor> getCounselors() {
        String sql = "SELECT * FROM Counselors";

        List<Counselor> Counselors = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet result = stmt.executeQuery();

            while (result.next()){
                Counselor cs = new Counselor();

                cs.setName(result.getString("name"));
                cs.setSpecialization(result.getString("specialization"));
                cs.setAvailability(result.getBoolean("availability"));

                Counselors.add(cs);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return Counselors;
    }
}