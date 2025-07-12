package main.bc.service;


import main.bc.dao.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthServiceImpl implements AuthService  {
    private ArrayList<String> error_messages = new ArrayList<String>(); //used to store error messages

    //used for registration validation
    public boolean parseInput(String name, String surname, String phone, String email, String password){
        if (!name.isEmpty()){
            valid_name(name);
        } else {
            error_messages.add("Name must contain input");
        }

        if (!surname.isEmpty()) {
            valid_surname(surname);
        } else {
            error_messages.add("Surname must contain input");
        }

        if (!phone.isEmpty()) {
            valid_phone_nr(phone);
        } else {
            error_messages.add("Surname must contain input");
        }

        if (!email.isEmpty()) {
            valid_email_format(email);
        } else {
            error_messages.add("Email must contain input");
        }

        if (!password.isEmpty()){
            valid_password(password);
        } else {
            error_messages.add("Password must contain input");
        }

        //displays error messages if arraylist contains any items
        if (!error_messages.isEmpty()) {
            errorOutput();
            return false;
        }

        return true;
    }

    //used for login validation
    public boolean parseInput(String email, String password){
        if (!email.isEmpty()) {
            valid_email_format(email);
        } else {
            error_messages.add("Email must contain input");
        }

        if (!password.isEmpty()){
            valid_password(password);
        } else {
            error_messages.add("Password must contain input");
        }

        //displays error messages if arraylist contains any items
        if (!error_messages.isEmpty()) {
            errorOutput();
            return false;
        }

        return true;
    }

    private void errorOutput(){
        for (var item: error_messages) {
            System.out.println(item);
        }
    }

    public void valid_name(String name){
        if (!(name.length() > 2)){
            error_messages.add("Name must contain 2 or more characters");
        }
    }

    public void valid_surname(String surname){
        if (!(surname.length() > 2)){
            error_messages.add("Surname must contain 2 or more characters");
        }
    }

    public void valid_phone_nr(String phone) {
        if (!(phone.length() == 10)){
            error_messages.add("Phone number must contain 10 characters");
        }

        try {
            Integer.parseInt(phone);
        }
        catch (Exception e) {
            error_messages.add("Phone number may contain only numbers");
        }
    }

    public void valid_email_format(String email) {
        if (!email.contains("@")){
            error_messages.add("Email must contain a '@' character");
        }
    }

    public void valid_password(String password) {

    }

    public boolean isTokenValid(String token) {
        String sql = "SELECT 1 FROM users WHERE session_token = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}