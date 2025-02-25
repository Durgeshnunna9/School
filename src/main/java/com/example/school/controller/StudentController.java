package com.example.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.school.model.Student;
import com.example.school.service.StudentH2Service;

@RestController
public class StudentController {

    @Autowired
    public StudentH2Service studentH2Service;

    @GetMapping("/students")
    public ArrayList<Student> getStudents() {
        return studentH2Service.getStudents();
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable("studentId") int studentId) {
        return studentH2Service.getStudentById(studentId);
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentH2Service.addStudent(student);
    }

    @PostMapping("/students/bulk")
    public ResponseEntity<String> addMultipleStudents(@RequestBody List<Student> students) {
        int numberOfStudentsAdded = studentH2Service.addMultipleStudents(students);
        return ResponseEntity.ok("Number of students added: " + numberOfStudentsAdded);
    }

    @PutMapping("/students/{studentId}")
    public Student updateStudent(@PathVariable("studentId") int studentId, @RequestBody Student student) {
        return studentH2Service.updateStudent(studentId, student);
    }

    @DeleteMapping("/students/{studentId}")
    public void deleteStudent(@PathVariable("studentId") int studentId) {
        studentH2Service.deleteStudent(studentId);
    }

}