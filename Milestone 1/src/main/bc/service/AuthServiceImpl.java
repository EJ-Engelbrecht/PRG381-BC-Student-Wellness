package main.bc.service;

import main.bc.dao.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthServiceImpl implements AuthService {
    private final ArrayList<String> error_messages = new ArrayList<>();

    // used for registration validation
    public boolean parseInput(String name, String surname, String phone, String email, String password) {
        if (!name.isEmpty()) {
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
            error_messages.add("Phone number must contain input");
        }

        if (!email.isEmpty()) {
            valid_email_format(email);
        } else {
            error_messages.add("Email must contain input");
        }

        if (!password.isEmpty()) {
            valid_password(password);
        } else {
            error_messages.add("Password must contain input");
        }

        if (!error_messages.isEmpty()) {
            errorOutput();
            return false;
        }

        return true;
    }

    // used for login validation
    public boolean parseLoginInput(String email, String password) {
        if (!email.isEmpty()) {
            valid_email_format(email);
        } else {
            error_messages.add("Email must contain input");
        }

        if (!password.isEmpty()) {
            valid_password(password);
        } else {
            error_messages.add("Password must contain input");
        }

        if (!error_messages.isEmpty()) {
            errorOutput();
            return false;
        }

        return true;
    }

    private void errorOutput() {
        for (String item : error_messages) {
            System.out.println(item);
        }
    }

    public void valid_name(String name) {
        if (name.length() <= 2) {
            error_messages.add("Name must contain more than 2 characters");
        }
    }

    public void valid_surname(String surname) {
        if (surname.length() <= 2) {
            error_messages.add("Surname must contain more than 2 characters");
        }
    }

    public void valid_phone_nr(String phone) {
        if (phone.length() != 10) {
            error_messages.add("Phone number must contain exactly 10 digits");
            return;
        }

        try {
            Long.parseLong(phone);
        } catch (NumberFormatException e) {
            error_messages.add("Phone number may contain only numbers");
        }
    }

    public void valid_email_format(String email) {
        if (!email.contains("@")) {
            error_messages.add("Email must contain a '@' character");
        }
    }

    public void valid_password(String password) {
        if (password.length() != 10) {
            error_messages.add("Password must contain exactly 10 characters");
        }

        if (number_count(password) < 3) {
            error_messages.add("Password must contain at least 3 numbers");
        }
    }

    private int number_count(String password) {
        int count = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                count++;
            }
        }
        return count;
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
