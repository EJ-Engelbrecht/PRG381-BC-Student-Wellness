package com.model;

import java.sql.Time;
import java.util.Date;

public class Appointment {
    private String student;
    private String counselor;
    private Date date;
    private Time time;
    private String status;


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

    public Date getDate() {
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


}