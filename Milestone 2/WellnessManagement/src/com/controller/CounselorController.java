package com.controller;

import com.dao.CounselorDAOImpl;
import com.dao.DBConnection;
import com.model.Counselor;

import java.sql.Connection;
import java.util.List;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class CounselorController {

    private CounselorDAOImpl counselorDAOImpl;  // ✅ correct type

    public CounselorController() {
        try {
            Connection conn = DBConnection.getConnection();
            counselorDAOImpl = new CounselorDAOImpl(conn);  // ✅ initialize properly
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCounselor(Counselor counselor) {
        counselorDAOImpl.registerCounselor(counselor);
    }

    public boolean deleteCounselor(int id) {
        counselorDAOImpl.deleteCounselor(id);  // ✅ use the correct DAO
        return true;
    }

    public List<Counselor> getAllCounselors() {
        return counselorDAOImpl.getCounselors();  // ✅ use the correct DAO
    }
    
    public void updateCounselor(Counselor counselor) {
    counselorDAOImpl.updateCounselor(counselor);
    }
    public void populateTable(JTable table) {
    List<Counselor> counselorList = getAllCounselors();

    DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Name", "Specialization", "Availability"}, 0);

    for (Counselor c : counselorList) {
        model.addRow(new Object[]{
            c.getId(),
            c.getName(),
            c.getSpecialization(),
            c.isAvailable() ? "Available" : "Unavailable"
        });
    }

    table.setModel(model);
}
}


