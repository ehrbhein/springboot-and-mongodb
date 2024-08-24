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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@SpringBootApplication
public class SpringbootAndMongodbApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootAndMongodbApplication.class, args);
  }


  /*
   * @info: Succeeding application runs will throw and error when  `auto-index-creation` is enabled
   * */
  @Bean
  CommandLineRunner runner(StudentRepository studentRepository, MongoTemplate mongoTemplate) {
    return args -> {
      Address address = new Address("England", "London", "NE9");
      Student student = new Student("Jamila", "Ahmed", "jahmed@gmail.com", Gender.FEMALE, address, List.of("Computer Science"), BigDecimal.TEN, LocalDateTime.now());

      /*
       * @note: We implement a find criteria to search for existing students on the database.
       * It should only insert new student if no other students are found with the same email.
       */
      Criteria criteria = Criteria.where("email").is(student.getEmail());

      Query query = new Query();
      query.addCriteria(criteria);

      List<Student> students = mongoTemplate.find(query, Student.class);

      if (students.size() > 1) {
        throw new IllegalStateException("Found more than one student with email: " + student.getEmail());

      } else if (!students.isEmpty()) {
        System.out.println(student + " already exists.");

      } else {
        studentRepository.insert(student);

      }
    };
  }
}
