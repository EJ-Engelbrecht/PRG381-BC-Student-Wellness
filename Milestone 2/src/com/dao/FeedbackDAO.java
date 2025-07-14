package com.dao;

import com.model.Feedback;

import java.util.List;

//Methods to interact with the Feedback Table
interface FeedbackDAO {
    List<Feedback> getFeedback();
    void registerFeedback(Feedback feedback);
    void updateFeedback(Feedback feedback);
    void deleteFeedback(String student);
}