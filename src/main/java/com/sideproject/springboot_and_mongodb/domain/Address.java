package com.sideproject.springboot_and_mongodb.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Address {

  private String country;
  private String city;
  private String postCode;

}
