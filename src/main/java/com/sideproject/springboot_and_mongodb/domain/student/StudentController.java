package com.sideproject.springboot_and_mongodb.domain.student;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @GetMapping
  @RequestMapping("api/v1/students")
  public List<Student> fetchAllStudents() {
    return studentService.getAllStudents();
  }
}
