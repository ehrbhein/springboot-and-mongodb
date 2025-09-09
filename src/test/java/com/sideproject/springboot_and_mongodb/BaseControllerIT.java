package com.sideproject.springboot_and_mongodb;

import com.sideproject.springboot_and_mongodb.domain.model.student.CustomMongoContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class BaseControllerIT {

  @Autowired
  protected MockMvcTester mockMvc;

  @Container
  protected static MongoDBContainer mongoDBContainer = CustomMongoContainer.getInstance();

}
