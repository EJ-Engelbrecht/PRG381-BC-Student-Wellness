//methods to access data from database in StudentDAOImpl class
interface StudentDAO {
    List<Student> getStudents(int no);
    List<Student> getStudents();
    void registerStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(int studentNo);
}