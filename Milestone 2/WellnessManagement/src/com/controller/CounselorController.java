package com.controller;

import com.dao.CounselorDAOImpl;
import com.dao.DBConnection;
import com.model.Counselor;
import com.dao.CounselorChangeListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CounselorController {

    // === Singleton Instance ===
    private static CounselorController instance;

    public static CounselorController getInstance() {
        if (instance == null) {
            instance = new CounselorController();
        }
        return instance;
    }

    // === Internal DAO and Listener List ===
    private final CounselorDAOImpl counselorDAOImpl;
    private final List<CounselorChangeListener> listeners = new ArrayList<>();

    // === Private Constructor ===
    private CounselorController() {
        Connection conn = DBConnection.getConnection();
        counselorDAOImpl = new CounselorDAOImpl(conn);
    }

    // === CRUD Operations ===
    public void addCounselor(Counselor counselor) {
        counselorDAOImpl.registerCounselor(counselor);
        notifyCounselorListChanged();
    }

    public boolean deleteCounselor(int id) {
        counselorDAOImpl.deleteCounselor(id);
        notifyCounselorListChanged();
        return true;
    }

    public void updateCounselor(Counselor counselor) {
        counselorDAOImpl.updateCounselor(counselor);
        notifyCounselorListChanged();
    }

    public List<Counselor> getAllCounselors() {
        return counselorDAOImpl.getCounselors();
    }

    public List<String> getFormattedCounselorList() {
        List<String> formattedList = new ArrayList<>();
        List<Counselor> counselors = counselorDAOImpl.getAvailableCounselors();
        for (Counselor c : counselors) {
            formattedList.add(c.getSpecialization() + " - " + c.getName());
        }
        return formattedList;
    }

    public List<String> getFormattedCounselorNames() {
        return getFormattedCounselorList(); // same logic, no need to duplicate
    }

    public void populateTable(JTable table) {
        List<Counselor> counselorList = getAllCounselors();

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Name", "Specialization", "Availability"}, 0
        );

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

    // === Observer Logic ===
    public void addCounselorChangeListener(CounselorChangeListener listener) {
        listeners.add(listener);
        System.out.println("✅ Listener added. Total: " + listeners.size());
    }

    private void notifyCounselorListChanged() {
        System.out.println("🔔 Notifying " + listeners.size() + " listener(s) of counselor list change.");
        for (CounselorChangeListener listener : listeners) {
            listener.onCounselorListChanged();
        }
    }
}
