package com.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Feedback {
    private int id;
    private String student;
    private int rating;
    private String Comments;

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

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

    public String getComments(){
        return this.Comments;
    }
    public void setComments(String comments) {
        this.Comments = comments;
    }


    public Feedback() {}
    public Feedback(int id, String student, int rating, String comments) {
        this.id = id;
        this.student = student;
        this.rating = rating;
        this.Comments = comments;
    }
}