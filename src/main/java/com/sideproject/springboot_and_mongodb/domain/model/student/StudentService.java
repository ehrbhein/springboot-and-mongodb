package com.sideproject.springboot_and_mongodb.domain.model.student;

import com.sideproject.springboot_and_mongodb.domain.Address;
import com.sideproject.springboot_and_mongodb.domain.Gender;
import com.sideproject.springboot_and_mongodb.domain.model.StudentRequest;
import com.sideproject.springboot_and_mongodb.domain.model.StudentResponse;
import com.sideproject.springboot_and_mongodb.domain.model.StudentResponse.GenderEnum;
import com.sideproject.springboot_and_mongodb.domain.model.error.APIBadRequestException;
import com.sideproject.springboot_and_mongodb.domain.model.error.APIConflictException;
import com.sideproject.springboot_and_mongodb.domain.model.error.APINotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;

  public List<StudentResponse> getAllStudents() {
    List<StudentResponse> studentResponses = new ArrayList<>();
    List<Student> allStudents = studentRepository.findAll();

    for (Student student : allStudents) {
      StudentResponse responseItem = buildStudentResponse(student);
      studentResponses.add(responseItem);
    }

    return studentResponses;
  }

  public StudentResponse addStudent(StudentRequest request) {
    Optional<Gender> studentGender = getGender(request.getGender().toString());

    if (studentGender.isEmpty()) {
      throw new APIBadRequestException("Bad request body.");
    }

    Address studentAddress = Address.builder()
        .city(request.getCity())
        .postCode(request.getPostCode())
        .country(request.getCountry())
        .build();

    Student newStudent = new Student(
        request.getFirstName(),
        request.getLastName(),
        request.getEmail(),
        studentGender.get(),
        studentAddress,
        request.getFavouriteSubjects(),
        BigDecimal.valueOf(request.getTotalSpentInBooks()),
        LocalDateTime.now()
    );

    Optional<Student> existingStudent = studentRepository.findStudentByEmail(request.getEmail());

    if (existingStudent.isPresent()) {
      throw new APIConflictException("Student already exists");
    }
    return buildStudentResponse(studentRepository.insert(newStudent));
  }

  public Student getStudentById(String id) {
    Optional<Student> existingStudent = studentRepository.findStudentById(id);

    if (existingStudent.isEmpty()) {
      throw new APINotFoundException("Student not found");
    } else {
      return existingStudent.get();
    }
  }


  private Optional<Gender> getGender(String genderString) {
    for (Gender gender : Gender.values()) {
      if (gender.name().equalsIgnoreCase(genderString)) {
        return Optional.of(gender);
      }
    }

    return Optional.empty();
  }

  private StudentResponse buildStudentResponse(Student student) {
    GenderEnum responseGender = null;

    for (GenderEnum gender : GenderEnum.values()) {
      if (gender.name().equalsIgnoreCase(student.getGender().toString())) {
        responseGender = gender;
      }
    }

    return new StudentResponse(
        student.getId(),
        student.getFirstName(),
        student.getLastName(),
        student.getEmail(),
        responseGender,
        student.getAddress().getCountry(),
        student.getAddress().getCity(),
        student.getAddress().getPostCode(),
        student.getFavouriteSubjects(),
        Integer.valueOf(student.getTotalSpentInBooks().toString()),
        student.getCreated().toString()
    );
  }


}
