package com.dao;

import com.model.*;
import java.util.List;

//Methods to interact with the Feedback Table
interface FeedbackDAO {
    List<Feedback> getAllFeedback();
    void registerFeedback(Feedback feedback);
    void updateFeedback(Feedback feedback);
    void deleteFeedback(int id);
}
