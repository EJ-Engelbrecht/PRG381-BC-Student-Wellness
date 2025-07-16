package main.bc.servlet;

import main.bc.dao.DBConnection;
import main.bc.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class RegisterServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        main.bc.util.DBInitializer.initialize(); // Ensures DB and 'users' table exist
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentNumber = request.getParameter("student_number");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            // Check if user already exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE email = ? OR student_number = ?");
            checkStmt.setString(1, email);
            checkStmt.setString(2, studentNumber);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                request.setAttribute("errorMessage", "Email or student number already exists.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            String hashedPassword = PasswordUtil.hashPassword(password);

            PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO users (student_number, name, surname, email, phone, password) VALUES (?, ?, ?, ?, ?, ?)"
            );
            insertStmt.setString(1, studentNumber);
            insertStmt.setString(2, name);
            insertStmt.setString(3, surname);
            insertStmt.setString(4, email);
            insertStmt.setString(5, phone);
            insertStmt.setString(6, hashedPassword);

            insertStmt.executeUpdate();

            request.setAttribute("successMessage", "Registration successful! You can now log in.");
            request.getRequestDispatcher("register.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Server error. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
