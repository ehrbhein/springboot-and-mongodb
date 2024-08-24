package com.sideproject.springboot_and_mongodb;

import com.sideproject.springboot_and_mongodb.domain.Address;
import com.sideproject.springboot_and_mongodb.domain.Gender;
import com.sideproject.springboot_and_mongodb.domain.student.Student;
import com.sideproject.springboot_and_mongodb.domain.student.StudentRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootAndMongodbApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootAndMongodbApplication.class, args);
  }


  @Bean
  CommandLineRunner runner(StudentRepository studentRepository) {
    return args -> {
      Address address = new Address("England", "London", "NE9");
      Student student = new Student("Jamila", "Ahmed", "jahmed@gmail.com", Gender.FEMALE, address, List.of("Computer Science"), BigDecimal.TEN, LocalDateTime.now());

      studentRepository.insert(student);
    };
  }
}
