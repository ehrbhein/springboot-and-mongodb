package com.sideproject.springboot_and_mongodb.domain.model.student;

import org.testcontainers.containers.MongoDBContainer;

/**
 * Singleton class for using {@link MongoDBContainer} test container for integration testing.
 */
public class CustomMongoContainer extends MongoDBContainer {

  private static final String IMAGE_VERSION = "mongo:7";
  private static CustomMongoContainer container;

  public CustomMongoContainer() {
    super(IMAGE_VERSION);
  }

  public static CustomMongoContainer getInstance() {
    if (container == null) {
      container = new CustomMongoContainer();
    }

    return container;
  }

  public void start() {
    super.start();
    System.setProperty("spring.data.mongodb.uri", container.getReplicaSetUrl());
  }

  public void stop() {
    super.stop();
  }
}
