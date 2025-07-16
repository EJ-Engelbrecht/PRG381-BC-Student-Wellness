package com.controller;

import com.dao.CounselorDAOImpl;
import com.model.Counselor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.dao.DBConnection.getConnection;
import javax.swing.*;

public class CounselorController {

    private CounselorDAOImpl counselorDAO;

    public CounselorController() {
        try {
            Connection conn = getConnection();
            this.counselorDAO = new CounselorDAOImpl(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
            e.printStackTrace();
        }
    }

    public boolean addCounselor(Counselor counselor) {
        counselorDAO.registerCounselor(counselor);
        return true;
    }

    public boolean deleteCounselor(int id) {
        counselorDAO.deleteCounselor(id);
        
        return false;
    }

    public List<Counselor> getAllCounselors() {
        return counselorDAO.getCounselors();
    }
}
