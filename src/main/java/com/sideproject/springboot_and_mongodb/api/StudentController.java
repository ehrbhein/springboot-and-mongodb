package com.sideproject.springboot_and_mongodb.api;

import com.sideproject.springboot_and_mongodb.domain.model.student.Student;
import com.sideproject.springboot_and_mongodb.domain.model.student.StudentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

  private final StudentService studentService;

  @Autowired
  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping
  @RequestMapping("/api/v1/students")
  public List<Student> fetchAllStudents() {
    return studentService.getAllStudents();
  }

  @PostMapping(value = "/api/v1/students")
  public Student addStudent(@RequestBody @Valid Student student) {

    return studentService.addStudent(student);
  }

}
