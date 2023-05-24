/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.model.StudentCourse;
import com.example.demo.service.CourseService;
import com.example.demo.service.StudentCourseService;
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
@RequestMapping("/course/")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping("")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Course> getAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/count")
    @CrossOrigin(origins = "http://localhost:3000")
    public Long getCorsesNumber() {
        return courseService.count();
    }

    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
    public String createStudent(@RequestBody Course course) {
        try {
            if (!courseService.existsById(course.getId())) {
                courseService.save(course);
                return "success";
            }
        } catch (Exception e) {
        }
        return "error";
    }

    public List<StudentCourse> getStudentCoursesObjects(@PathVariable("id") Integer id) {
        List<StudentCourse> studentCourses = studentCourseService.findAll();
        studentCourses = studentCourses.stream()
                .filter(sc -> sc.getCourse().getId() == id)
                .collect(Collectors.toList());
        return studentCourses;
    }

    @PostMapping("/remove")
    @CrossOrigin(origins = "http://localhost:3000")
    public void deleteStudent(@RequestBody Course course) {
        for (StudentCourse studentCoursesObject : getStudentCoursesObjects(course.getId())) {
            studentCourseService.delete(studentCoursesObject);
        }
        courseService.delete(course);
    }

    @PostMapping("/update")
    @CrossOrigin(origins = "http://localhost:3000")
    public String updateStudent(@RequestBody Course course) {
        try {
            if (course != null) {
                courseService.save(course);
                return "success";
            }
        } catch (Exception e) {
        }
        return "error";
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Integer id) {
        Course course = courseService.findById(id).get();
        if (course != null) {
            return ResponseEntity.ok(course);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
