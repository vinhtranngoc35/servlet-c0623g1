package com.example.demo1.service;

import com.example.demo1.model.Student;
import com.example.demo1.model.enumration.EGender;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private static final List<Student> students;
    private static int idCurrent;

    static {
        students = new ArrayList<>();
        students.add(new Student(++idCurrent, "Thang", Date.valueOf(LocalDate.now()), EGender.MALE));
        students.add(new Student(++idCurrent, "Duc", Date.valueOf(LocalDate.now()), EGender.MALE));
        students.add(new Student(++idCurrent, "Huy", Date.valueOf(LocalDate.now()), EGender.MALE));
    }

    public List<Student> getStudents(){
        return students;
    }

    public void addStudent(String name, String dob, String gender){
        Student student = new Student(++idCurrent,name, Date.valueOf(dob), EGender.valueOf(gender));
        students.add(student);
    }
}