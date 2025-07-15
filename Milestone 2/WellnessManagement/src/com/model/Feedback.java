package com.model;

public class Feedback {
    private int id;
    private String student;
    private int rating;
    private String comments;

    public Feedback() {}

    public Feedback(int id, String student, int rating, String comments) {
        this.id = id;
        this.student = student;
        this.rating = rating;
        this.comments = comments;
    }

    public Feedback(String student, int rating, String comments) {
        this.student = student;
        this.rating = rating;
        this.comments = comments;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getStudent() { return student; }
    public void setStudent(String student) { this.student = student; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }
}
