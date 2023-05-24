/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.StudentCourse;
import com.example.demo.model.StudentCourseId;
import com.example.demo.service.StudentCourseService;
import java.util.ArrayList;
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
@RequestMapping("/studentCourse/")
public class StudentCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping("/studentCourses/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Course> getStudentCourses(@PathVariable("id") Integer id) {
        List<StudentCourse> studentCourses = studentCourseService.findAll();
        List<Course> courses = studentCourses.stream()
                .filter(sc -> sc.getStudent().getId() == id)
                .map(sc -> sc.getCourse())
                .collect(Collectors.toList());
        return courses;
    }


    public List<StudentCourse> getStudentCoursesObjects(@PathVariable("id") Integer id) {
        List<StudentCourse> studentCourses = studentCourseService.findAll();
        studentCourses = studentCourses.stream()
                .filter(sc -> sc.getStudent().getId() == id)
                .collect(Collectors.toList());
        return studentCourses;
    }

    @PostMapping("/addAll/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public String AddCoursesToStudent(@RequestBody List<StudentCourse> studentCourses, @PathVariable("id") Integer id) {
        try {
            List<StudentCourse> studentExistCourses = getStudentCoursesObjects(id);

            List<StudentCourse> coursesToDelete = new ArrayList<>();

            studentExistCourses.forEach(c -> {
                if (!studentCourses.contains(c)) {
                    coursesToDelete.add(c);
                }
            });

            coursesToDelete.forEach(studentCourseService::delete);

            studentCourses.forEach(studentCourseService::save);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<StudentCourse> getCourseById(@PathVariable("id") StudentCourseId id) {
        StudentCourse studentCourse = studentCourseService.findById(id).get();
        if (studentCourse != null) {
            return ResponseEntity.ok(studentCourse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
