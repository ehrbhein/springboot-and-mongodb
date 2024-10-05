package com.sideproject.springboot_and_mongodb.domain.student;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }
}
