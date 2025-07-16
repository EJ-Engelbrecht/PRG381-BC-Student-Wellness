package com.model;

import java.sql.Time;
import java.util.Date;
import java.time.LocalTime;

public class Appointment {
    private int id;
    private String student;
    private String counselor;
    private Date date;
    private Time time;
    private String status;

    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getStudent(){
        return this.student;
    }
    public void setStudent(String student) {
        this.student = student;
    }
    
    public String getCounselor(){
        return this.counselor;
    }
    public void setCounselor(String Counselor) {
        this.counselor = Counselor;
    }

    public java.util.Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return this.time;
    }
    public void setTime(Time time) {
        this.time = time;
    }

    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Appointment(){}
    public Appointment(int id,String student, String counselor, java.util.Date date, Time time, String Status) {
        this.id = id;
        this.student = student;
        this.counselor = counselor;
        this.date = date;
        this.time = time;
        this.status = Status;
    }
}
