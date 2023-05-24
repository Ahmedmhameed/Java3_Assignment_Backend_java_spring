/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.model.StudentCourse;
import com.example.demo.service.StudentCourseService;
import com.example.demo.service.StudentService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author AHMED
 */
@RestController
@RequestMapping("/student/")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Student> getAllData() {
        return studentService.findAll();
    }

    @GetMapping("/count")
    @CrossOrigin(origins = "http://localhost:3000")
    public Long getStudentNumber() {
        return studentService.count();
    }

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
    public String createStudent(@RequestBody Student student) {
        try {
            if (student != null && !studentService.existsById(student.getId())) {
                studentService.save(student);
                return "success";
            }
        } catch (Exception e) {
        }
        return "error";

    }

    @PostMapping("/remove")
    @CrossOrigin(origins = "http://localhost:3000")
    public void deleteStudent(@RequestBody Student student) {
        for (StudentCourse studentCoursesObject : getStudentCoursesObjects(student.getId())) {
            studentCourseService.delete(studentCoursesObject);
        }
        studentService.delete(student);
    }

    public List<StudentCourse> getStudentCoursesObjects(@PathVariable("id") Integer id) {
        List<StudentCourse> studentCourses = studentCourseService.findAll();
        studentCourses = studentCourses.stream()
                .filter(sc -> sc.getStudent().getId() == id)
                .collect(Collectors.toList());
        return studentCourses;
    }

    @PostMapping("/update")
    @CrossOrigin(origins = "http://localhost:3000")
    public String updateStudent(@RequestBody Student student) {
        try {
            if (student != null) {
                studentService.save(student);
                return "success";
            }
        } catch (Exception e) {

        }
        return "error";
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Integer id) {
        Student student = studentService.findById(id).get();
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/name/{name}")
    @CrossOrigin(origins = "http://localhost:3000")

    public List<Student> searchByName(@PathVariable("name") String name) {
        return studentService
                .findAll()
                .stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @GetMapping("/search/major/{major}")
    @CrossOrigin(origins = "http://localhost:3000")

    public List<Student> searchByMajor(@PathVariable("major") String major) {
        return studentService
                .findAll()
                .stream()
                .filter(s -> s.getMajor().toLowerCase().contains(major.toLowerCase()))
                .collect(Collectors.toList());
    }



    @GetMapping("/search/grade/{bigger}/{smaller}")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Student> searchByGradeIn(@PathVariable("bigger") Double bigger, @PathVariable("smaller") Double smaller) {
        return studentService
                .findAll()
                .stream()
                .filter(s -> s.getGrade() >= bigger && s.getGrade() <= smaller)
                .collect(Collectors.toList());
    }

}
