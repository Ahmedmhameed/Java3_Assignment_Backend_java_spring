/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.demo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author AHMED
 */
@Entity
@Table(name = "student_courses")

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentCourse {

    @EmbeddedId
    private StudentCourseId id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("studentId")
    private Student student;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("courseId")
    private Course course;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

}
