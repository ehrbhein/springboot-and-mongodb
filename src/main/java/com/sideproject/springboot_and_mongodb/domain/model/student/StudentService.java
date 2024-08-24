package com.sideproject.springboot_and_mongodb.domain.model.student;

import com.sideproject.springboot_and_mongodb.domain.Address;
import com.sideproject.springboot_and_mongodb.domain.model.PagedStudentResponse;
import com.sideproject.springboot_and_mongodb.domain.model.PagedStudentResponsePage;
import com.sideproject.springboot_and_mongodb.domain.model.StudentRequest;
import com.sideproject.springboot_and_mongodb.domain.model.StudentResponse;
import com.sideproject.springboot_and_mongodb.domain.model.error.APIConflictException;
import com.sideproject.springboot_and_mongodb.domain.model.error.APINotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;

  public PagedStudentResponse getAllStudents(Pageable pageable) {
    List<StudentResponse> studentResponses = new ArrayList<>();
    Page<Student> allStudents = studentRepository.findAll(pageable);

    for (Student student : allStudents) {
      StudentResponse responseItem = buildStudentResponse(student);
      studentResponses.add(responseItem);
    }

    return new PagedStudentResponse(studentResponses,
        new PagedStudentResponsePage(
            pageable.getPageSize(),
            allStudents.getNumberOfElements(),
            allStudents.getTotalPages(),
            allStudents.getNumber()));
  }

  public StudentResponse addStudent(StudentRequest request) {
    Optional<Student> existingStudent = studentRepository.findStudentByEmail(request.getEmail());

    if (existingStudent.isPresent()) {
      throw new APIConflictException("Student already exists");
    }

    Address studentAddress = Address.builder()
        .city(request.getCity())
        .postCode(request.getPostCode())
        .country(request.getCountry())
        .build();

    Student newStudent = Student.builder()
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .email(request.getEmail())
        .gender(request.getGender())
        .address(studentAddress)
        .favouriteSubjects(request.getFavouriteSubjects())
        .totalSpentInBooks(BigDecimal.valueOf(request.getTotalSpentInBooks()))
        .created(LocalDateTime.now())
        .build();

    return buildStudentResponse(studentRepository.insert(newStudent));
  }

  public StudentResponse updateStudent(String id, StudentRequest request) {
    Optional<Student> existingStudent = studentRepository.findStudentById(id);

    if (existingStudent.isEmpty()) {
      throw new APINotFoundException("Student not found");
    }

    Student studentWithUpdatedInfo = updateStudentWithInfoFromRequest(existingStudent.get(), request);
    return buildStudentResponse(studentRepository.save(studentWithUpdatedInfo));
  }

  public StudentResponse getStudentById(String id) {
    Optional<Student> existingStudent = studentRepository.findStudentById(id);

    if (existingStudent.isEmpty()) {
      throw new APINotFoundException("Student not found");
    } else {
      return buildStudentResponse(existingStudent.get());
    }
  }

  private Student updateStudentWithInfoFromRequest(Student student, StudentRequest request) {
    student.setFirstName(request.getFirstName());
    student.setLastName(request.getLastName());
    student.setEmail(request.getEmail());
    student.setGender(request.getGender());
    student.getAddress().setCity(request.getCity());
    student.getAddress().setCountry(request.getCountry());
    student.getAddress().setPostCode(request.getPostCode());
    student.setFavouriteSubjects(request.getFavouriteSubjects());

    return student;
  }

  private StudentResponse buildStudentResponse(Student student) {
    return new StudentResponse(
        student.getId(),
        student.getFirstName(),
        student.getLastName(),
        student.getEmail(),
        student.getGender(),
        student.getAddress().getCountry(),
        student.getAddress().getCity(),
        student.getAddress().getPostCode(),
        student.getFavouriteSubjects(),
        Integer.valueOf(student.getTotalSpentInBooks().toString()),
        student.getCreated().toString());
  }


}
