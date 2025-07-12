package main.bc.model;

//student model class to hold data when writing data to and reading data from the database
public class Student {
    //Student model class properties
    private int student_nr;
    private String name;
    private String surname;
    private String phone;
    private String email;
    private String password;
    private String sessionToken;


    //getters and setters for the Student Model class
    public int getStudent_no() {
        return this.student_nr;
    }
    public void setStudent_no(int no) {
        this.student_nr = no;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionToken() { return sessionToken; }
    public void setSessionToken(String sessionToken) { this.sessionToken = sessionToken; }

}