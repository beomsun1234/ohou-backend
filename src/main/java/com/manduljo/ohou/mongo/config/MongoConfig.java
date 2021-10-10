package com.manduljo.ohou.mongo.config;

import com.mongodb.MongoClientSettings;
import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.manduljo.ohou.mongo.repository")
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoClientConfiguration {

  private static final String MONGODB_DATABASE = "ohou";

  @Override
  protected void configureClientSettings(MongoClientSettings.Builder builder) {
    builder.uuidRepresentation(UuidRepresentation.STANDARD);
  }

  @Override
  protected String getDatabaseName() {
    return MONGODB_DATABASE;
  }

}
