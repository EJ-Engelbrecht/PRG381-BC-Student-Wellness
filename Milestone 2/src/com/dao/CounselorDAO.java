package com.dao;

import com.model.Counselor;

import java.util.List;

//Methods to interact with the Counselor Table
interface CounselorDAO {
    List<Counselor> getCounselors();
    void registerCounselor(Counselor counselor);
    void updateCounselor(Counselor counselor);
    void deleteCounselor(String name);
}