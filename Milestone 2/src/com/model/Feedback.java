package com.model;

import java.util.ArrayList;

public class Feedback {
    private String student;
    private int rating;
    private ArrayList<String> Comments;

    public String getStudent() {
        return this.student;
    }
    public void setStudent(String student) {
        this.student = student;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        if (rating > 0 && rating <=10){
            this.rating = rating;
        }
    }

    public ArrayList<String> getComments(){
        return Comments;
    }
    public void setComments(String comment) {
        Comments.add(comment);
    }
}