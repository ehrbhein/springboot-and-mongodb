package com.sideproject.springboot_and_mongodb.domain.model.student;

import com.sideproject.springboot_and_mongodb.domain.Address;
import com.sideproject.springboot_and_mongodb.domain.model.GenderEnum;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class Student {

  @Id
  private String id;
  private String firstName;
  private String lastName;
  @Indexed(unique = true)
  private String email;
  private GenderEnum gender;
  private Address address;
  private List<String> favouriteSubjects;
  private BigDecimal totalSpentInBooks;
  private LocalDateTime created;

}
