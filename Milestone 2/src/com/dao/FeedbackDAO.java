package com.dao;

import com.model.*;
import java.util.ArrayList;

//Methods to interact with the Feedback Table
interface FeedbackDAO {
    ArrayList<Feedback> getFeedback();
    void registerFeedback(Feedback feedback);
    void updateFeedback(Feedback feedback);
    void deleteFeedback(String student);
}