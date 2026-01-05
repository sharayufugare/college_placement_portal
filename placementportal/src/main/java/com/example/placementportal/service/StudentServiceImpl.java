/*package com.example.placementportal.service;

import com.example.placementportal.model.Student;
import com.example.placementportal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student registerStudent(Student student) {
        // You can add password hashing (e.g., BCrypt) here
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> loginStudent(String email, String password) {
        Optional<Student> studentOpt = studentRepository.findByEmail(email);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            if (student.getPassword().equals(password)) {
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setFullName(updatedStudent.getFullName());
            student.setUsername(updatedStudent.getUsername());
            student.setCollege(updatedStudent.getCollege());
            student.setProfileImgUrl(updatedStudent.getProfileImgUrl());
            return studentRepository.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

}
*/
