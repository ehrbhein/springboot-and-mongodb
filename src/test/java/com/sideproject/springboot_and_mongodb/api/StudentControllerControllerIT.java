package com.sideproject.springboot_and_mongodb.api;

import static com.sideproject.springboot_and_mongodb.domain.model.GenderEnum.MALE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import com.sideproject.springboot_and_mongodb.BaseControllerIT;
import com.sideproject.springboot_and_mongodb.domain.Address;
import com.sideproject.springboot_and_mongodb.domain.model.student.Student;
import com.sideproject.springboot_and_mongodb.domain.model.student.StudentRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * Integration test for {@link StudentController}
 * <p>
 * <p>Resources used for this integration test example can be found on:</p>
 *   <ul>
 *     <li>https://www.baeldung.com/spring-boot-testing</li>
 *     <li>https://www.baeldung.com/java-mongodb-testcontainers</li>
 *     <li>https://assertj.github.io/doc/#assertj-core</li>
 *   </ul>
 * </p>
 */
class StudentControllerControllerIT extends BaseControllerIT {

  @Autowired
  StudentRepository repository;

  @AfterEach
  void cleanUp() {
    repository.deleteAll();
  }

  @AfterAll
  static void tearDown() {
    mongoDBContainer.stop();
  }

  @Test
  void should_respond_with_expected_response_when_single_student_is_fetched() {
    // given:
    Student testStudent = Student.builder()
        .firstName("firstName")
        .lastName("lastName")
        .email("sample@email.com")
        .gender(MALE)
        .address(Address.builder()
            .country("country")
            .city("city")
            .postCode("P0stC0de")
            .build())
        .favouriteSubjects(List.of("Subject1"))
        .totalSpentInBooks(BigDecimal.valueOf(200))
        .created(LocalDateTime.now())
        .build();
    String studentId = repository.save(testStudent).getId();

    // when and then:
    assertThat(mockMvc.get().uri("/api/v1/students/%s".formatted(studentId))
        .contentType(MediaType.APPLICATION_JSON))
        .hasStatusOk()
        .bodyJson()
        .extractingPath("$")
        .asMap()
        .contains(entry("id", studentId))
        .contains(entry("firstName", "firstName"))
        .contains(entry("gender", "MALE"))
        .contains(entry("email", "sample@email.com"));
  }

  @Test
  void should_add_new_student() {
    // given:
    //language=JSON
    String requestBody =
        """
            {
              "firstName": "testFirstName",
              "lastName": "testLastName",
              "email": "test@gmail.com",
              "gender": "MALE",
              "country": "England",
              "city": "London Address 2",
              "postCode": "NE9",
              "favouriteSubjects": [
                "Computer Science"
              ],
              "totalSpentInBooks": 10
            }
            """;

    // when and then:
    assertThat(mockMvc.post().uri("/api/v1/students")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .hasStatusOk()
        .bodyJson()
        .extractingPath("$")
        .asMap()
        .contains(entry("firstName", "testFirstName"))
        .contains(entry("lastName", "testLastName"))
        .contains(entry("gender", "MALE"));
  }

  @Test
  void should_respond_400_if_gender_is_invalid() {
    // given:
    //language=JSON
    String requestBody =
        """
            {
              "firstName": "testFirstName",
              "lastName": "testLastName",
              "email": "test@gmail.com",
              "gender": "invalid_gender",
              "country": "England",
              "city": "London Address 2",
              "postCode": "NE9",
              "favouriteSubjects": [
                "Computer Science"
              ],
              "totalSpentInBooks": 10
            }
            """;

    // when and then:
    assertThat(mockMvc.post().uri("/api/v1/students")
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .hasFailed()
        .hasStatus(HttpStatus.BAD_REQUEST);
  }

  @Test
  void should_respond_with_all_fetchable_students() {
    // given:
    Student testStudent1 = Student.builder()
        .firstName("firstName1")
        .lastName("lastName1")
        .email("sample1@email.com")
        .gender(MALE)
        .address(Address.builder()
            .country("country")
            .city("city")
            .postCode("P0stC0de")
            .build())
        .favouriteSubjects(List.of("Subject1"))
        .totalSpentInBooks(BigDecimal.valueOf(200))
        .created(LocalDateTime.now())
        .build();

    Student testStudent2 = Student.builder()
        .firstName("firstName2")
        .lastName("lastName2")
        .email("sample2@email.com")
        .gender(MALE)
        .address(Address.builder()
            .country("country")
            .city("city")
            .postCode("P0stC0de")
            .build())
        .favouriteSubjects(List.of("Subject2"))
        .totalSpentInBooks(BigDecimal.valueOf(300))
        .created(LocalDateTime.now())
        .build();
    ;
    repository.saveAll(List.of(testStudent1, testStudent2));

    // when and then:
    var response = assertThat(mockMvc.get().uri("/api/v1/students")
        .contentType(MediaType.APPLICATION_JSON))
        .hasStatusOk().bodyJson();

    response
        .extractingPath("$.data[0]")
        .asMap()
        .contains(entry("firstName", "firstName1"));

    response
        .extractingPath("$.data[1]")
        .asMap()
        .contains(entry("firstName", "firstName2"));

    response
        .extractingPath("$.page")
        .asMap()
        .contains(entry("pageNumber", 0))
        .contains(entry("size", 10))
        .contains(entry("totalElements", 2))
        .contains(entry("totalPages", 1));
  }

  @Test
  void should_update_student_and_return_expected_response() {
    // given:
    Student testStudent = Student.builder()
        .firstName("firstName")
        .lastName("lastName")
        .email("sample@email.com")
        .gender(MALE)
        .address(Address.builder()
            .country("country")
            .city("city")
            .postCode("P0stC0de")
            .build())
        .favouriteSubjects(List.of("Subject1"))
        .totalSpentInBooks(BigDecimal.valueOf(200))
        .created(LocalDateTime.now())
        .build();
    String studentId = repository.save(testStudent).getId();

    //language=JSON
    String requestBody =
        """
            {
              "firstName": "updatedFirstName",
              "lastName": "updatedLastName",
              "email": "updated@gmail.com",
              "gender": "MALE",
              "country": "England",
              "city": "Updated London Address 2",
              "postCode": "NE9",
              "favouriteSubjects": [
                "Computer Science"
              ],
              "totalSpentInBooks": 20
            }
            """;

    // when and then:
    assertThat(mockMvc.put().uri("/api/v1/students/%s".formatted(studentId))
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON))
        .hasStatusOk()
        .bodyJson()
        .extractingPath("$")
        .asMap()
        .contains(entry("firstName", "updatedFirstName"))
        .contains(entry("lastName", "updatedLastName"))
        .contains(entry("city", "Updated London Address 2"))
        .contains(entry("postCode", "NE9"))
    ;
  }
}