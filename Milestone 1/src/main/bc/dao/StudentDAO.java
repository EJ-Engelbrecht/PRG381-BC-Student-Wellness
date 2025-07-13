package main.bc.dao;

import main.bc.model.Student;

import java.util.List;

interface StudentDAO {
    List<Student> getStudents(int no);
    List<Student> getStudents();
    void registerStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(int studentNo);
}