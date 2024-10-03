package com.example.school.service;

import com.example.school.model.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.stereotype.Service;

import java.util.*;

import com.example.school.model.Student;
import com.example.school.repository.StudentRepository;

@Service
public class StudentH2Service implements StudentRepository {

    @Autowired
    public JdbcTemplate db;

    @Override
    public ArrayList<Student> getStudents() {

        List<Student> studentList = db.query("SELECT * from student", new StudentRowMapper());

        ArrayList<Student> students = new ArrayList<>(studentList);
        return students;

    }

    @Override
    public int addMultipleStudents(List<Student> students) {
        String sql = "INSERT INTO STUDENT (studentName, gender, standard) VALUES (?, ?, ?)";

        // For each student, execute the insert query
        for (Student student : students) {
            db.update(sql, student.getStudentName(), student.getGender(), student.getStandard());
        }

        // Return the number of students added
        return students.size();
    }

    @Override
    public void deleteStudent(int studentId) {
        db.update("delete from student where studentId = ?", studentId);

    }

    @Override
    public Student updateStudent(int studentId, Student student) {

        if (student.getStudentName() != null) {
            db.update("update student set studentName = ? where studentId = ?", student.getStudentName(), studentId);
        }
        if (student.getGender() != null) {
            db.update("update student set gender = ? where studentId = ?", student.getGender(), studentId);
        }
        if (student.getStandard() != 0) {
            db.update("update student set standard = ? where studentId = ?", student.getStandard(), studentId);
        }
        return getStudentById(studentId);
    }

    @Override
    public Student addStudent(Student student) {

        db.update("insert into student(studentName, gender, standard) values (?, ?, ?)", student.getStudentName(),
                student.getGender(),
                student.getStandard());

        Student savedStudent = db.queryForObject(
                "select * from student where studentName = ? and gender = ? and standard = ?",
                new StudentRowMapper(), student.getStudentName(), student.getGender(), student.getStandard());
        return savedStudent;

    }

    @Override
    public Student getStudentById(int studentId) {

        try {

            Student student = db.queryForObject("select * from student where studentId = ?", new StudentRowMapper(),
                    studentId);

            return student;
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

}