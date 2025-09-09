package com.sideproject.springboot_and_mongodb.api;

import com.sideproject.springboot_and_mongodb.domain.model.PagedStudentResponse;
import com.sideproject.springboot_and_mongodb.domain.model.StudentRequest;
import com.sideproject.springboot_and_mongodb.domain.model.StudentResponse;
import com.sideproject.springboot_and_mongodb.domain.model.student.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StudentController {

  private final StudentService studentService;

  @GetMapping
  @RequestMapping("/api/v1/students")
  public ResponseEntity<PagedStudentResponse> fetchAllStudents(@PageableDefault Pageable pageable) {
    PagedStudentResponse response = studentService.getAllStudents(pageable);
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "/api/v1/students")
  public ResponseEntity<StudentResponse> addStudent(@RequestBody @Valid StudentRequest request) {
    StudentResponse response = studentService.addStudent(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/api/v1/students/{id}")
  public ResponseEntity<StudentResponse> fetchStudent(@PathVariable String id) {
    StudentResponse response = studentService.getStudentById(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/api/v1/students/{id}")
  public ResponseEntity<StudentResponse> updateStudent(@PathVariable String id, @RequestBody @Valid StudentRequest request) {
    StudentResponse response = studentService.updateStudent(id, request);
    return ResponseEntity.ok(response);
  }
}
