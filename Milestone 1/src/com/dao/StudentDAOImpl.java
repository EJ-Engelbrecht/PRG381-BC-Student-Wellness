public class StudentDAOImpl implements StudentDAO {
    private Connection conn;
    private AuthServiceImpl validate;

    public StudentDAOImpl(Connection conn){
        this.conn = conn;
        this.validate = new AuthServiceImpl();
    }

    public List<Student> getStudents(int no){
        String sql = "SELECT * FROM students WHERE student_no = ?";

        List<Student> students = new ArrayList<Student>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, no);

            ResultSet result = stmt.executeQuery();
            while (result.next()){
                Student st = new Student();

                st.setStudent_no(result.getInt("student_no"));
                st.setName(result.getString("name"));
                st.setSurname(result.getString("surname"));
                st.setPhone(result.getString("phone"));
                st.setEmail(result.getString("email"));
                st.setPassword(result.getString("password"));

                students.add(st);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return students;
    }

    public List<Student> getStudents() {
        String sql = "SELECT * FROM students";

        List<Student> students = new ArrayList<Student>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();

            while (result.next()){
                Student st = new Student();

                st.setStudent_no(result.getInt("student_no"));
                st.setName(result.getString("name"));
                st.setSurname(result.getString("surname"));
                st.setPhone(result.getString("phone"));
                st.setEmail(result.getString("email"));
                st.setPassword(result.getString("password"));

                students.add(st);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return students;
    }

    public List<Student> getStudents(String email) {
        String sql = "SELECT * FROM students WHERE email = ?";
        List<Student> students = new ArrayList<Student>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);

            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                Student st = new Student();

                st.setStudent_no(result.getInt("student_no"));
                st.setName(result.getString("name"));
                st.setSurname(result.getString("surname"));
                st.setPhone(result.getString("phone"));
                st.setEmail(result.getString("email"));
                st.setPassword(result.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return students;
    }


    public void registerStudent(Student student) {
        boolean isValid = validate.parseInput(
                student.getName(),
                student.getSurname(),
                student.getPhone(),
                student.getEmail(),
                student.getPassword()
        );

        if (!isValid){
            return;
        }

        String sql = "INSERT INTO students (student_nr, name, surname, phone, email, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getSurname());
            stmt.setString(3, student.getPhone());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, surname = ?, phone = ?, email = ?, password = ? WHERE student_nr = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getSurname());
            stmt.setString(3, student.getPhone());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getPassword());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Student record updated successfully.");
            } else {
                System.out.println("No student found with the given student number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int studentNo) {
        String sql = "DELETE FROM students WHERE student_nr = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentNo);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student record deleted successfully.");
            } else {
                System.out.println("No student found with the given student number.");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }