package com.sideproject.springboot_and_mongodb.domain.model.student;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {
  Optional<Student> findStudentByEmail(String email);
  Optional<Student> findStudentById(String id);
  Page<Student> findAll(Pageable pageable);
}
