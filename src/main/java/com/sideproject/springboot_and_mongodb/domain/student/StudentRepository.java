package com.sideproject.springboot_and_mongodb.domain.student;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {



}
