package com.sideproject.springboot_and_mongodb.domain.model.student;

import com.sideproject.springboot_and_mongodb.domain.model.error.APIConflictException;
import com.sideproject.springboot_and_mongodb.domain.model.error.APINotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  public Student addStudent(Student student) {
    Optional<Student> existingStudent = studentRepository.findStudentByEmail(student.getEmail());

    if (existingStudent.isPresent()) {
      throw new APIConflictException("Student already exists");
    }
    return studentRepository.insert(student);
  }

  public Student getStudentById(String id) {
    Optional<Student> existingStudent = studentRepository.findStudentById(id);

    if (existingStudent.isEmpty()) {
      throw new APINotFoundException("Student not found");
    } else {
      return existingStudent.get();
    }
  }
}
