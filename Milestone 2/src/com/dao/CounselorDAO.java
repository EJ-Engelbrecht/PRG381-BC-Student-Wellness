package com.dao;

import java.util.List;
import com.model.*;

//Methods to interact with the Counselor Table
interface CounselorDAO {
    List<Counselor> getCounselors();
    void registerCounselor(Counselor counselor);
    void updateCounselor(Counselor counselor);
    void deleteCounselor(String name);
}