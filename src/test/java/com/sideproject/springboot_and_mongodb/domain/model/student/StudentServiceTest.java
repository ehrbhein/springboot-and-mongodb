package com.sideproject.springboot_and_mongodb.domain.model.student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sideproject.springboot_and_mongodb.domain.Address;
import com.sideproject.springboot_and_mongodb.domain.model.GenderEnum;
import com.sideproject.springboot_and_mongodb.domain.model.PagedStudentResponse;
import com.sideproject.springboot_and_mongodb.domain.model.StudentRequest;
import com.sideproject.springboot_and_mongodb.domain.model.error.APIConflictException;
import com.sideproject.springboot_and_mongodb.domain.model.error.APINotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @InjectMocks
  private StudentService service;


  @Test
  void getAllStudents_should_return_paged_student_response() {
    // given:
    Pageable mockPageable = mock(Pageable.class);
    when(mockPageable.getPageSize()).thenReturn(10);

    Student testStudent = Student.builder()
        .firstName("firstName")
        .lastName("lastName")
        .email("sample@email.com")
        .gender(GenderEnum.MALE)
        .address(new Address("country", "city", "P0stC0de"))
        .favouriteSubjects(List.of("Subject1"))
        .totalSpentInBooks(BigDecimal.valueOf(200))
        .created(LocalDateTime.now())
        .build();
    PageImpl<Student> pagedStudentsResponse = new PageImpl<>(List.of(testStudent), mockPageable, 1);

    when(repository.findAll(mockPageable)).thenReturn(pagedStudentsResponse);

    // when:
    PagedStudentResponse actual = service.getAllStudents(mockPageable);

    // then:
    assertThat(actual)
        .isNotNull()
        .extracting(it -> it.getData().getFirst().getFirstName(),
            it -> it.getData().getFirst().getGender().toString(),
            it -> it.getPage().getTotalPages())
        .contains("firstName",
            GenderEnum.MALE.toString(),
            1);

  }

  @Test
  void addStudent_should_throw_APIConflictException_when_student_already_exists() {
    // given:
    Student testStudent = Student.builder()
        .firstName("firstName")
        .lastName("lastName")
        .email("sample@email.com")
        .gender(GenderEnum.MALE)
        .address(new Address("country", "city", "P0stC0de"))
        .favouriteSubjects(List.of("Subject1"))
        .totalSpentInBooks(BigDecimal.valueOf(200))
        .created(LocalDateTime.now())
        .build();

    StudentRequest request = new StudentRequest("firstName",
        "lastName",
        "sample@email.com",
        GenderEnum.MALE,
        "country",
        "city",
        "P0stC0de",
        List.of("Subject1"),
        200);

    when(repository.findStudentByEmail(request.getEmail())).thenReturn(Optional.of(testStudent));

    // when:
    Throwable thrown = catchThrowable(() -> service.addStudent(request));

    // then:
    assertThat(thrown)
        .isInstanceOf(APIConflictException.class)
        .hasMessageContaining("Student already exists");
  }

  @Test
  void updateStudent_should_throw_APINotFoundException_when_student_not_found() {
    // given:
    String studentId = UUID.randomUUID().toString();
    StudentRequest request = new StudentRequest("firstName",
        "lastName",
        "sample@email.com",
        GenderEnum.MALE,
        "country",
        "city",
        "P0stC0de",
        List.of("Subject1"),
        200);

    when(repository.findStudentById(studentId)).thenReturn(Optional.empty());

    // when:
    Throwable thrown = catchThrowable(() -> service.updateStudent(studentId, request));

    // then:
    assertThat(thrown)
        .isInstanceOf(APINotFoundException.class)
        .hasMessageContaining("Student not found");
  }

  @Test
  void getStudentById_should_throw_APINotFoundException_when_student_not_found() {
    // given:
    String studentId = UUID.randomUUID().toString();
    when(repository.findStudentById(studentId)).thenReturn(Optional.empty());

    // when:
    Throwable thrown = catchThrowable(() -> service.getStudentById(studentId));

    // then:
    assertThat(thrown)
        .isInstanceOf(APINotFoundException.class)
        .hasMessageContaining("Student not found");
  }

}