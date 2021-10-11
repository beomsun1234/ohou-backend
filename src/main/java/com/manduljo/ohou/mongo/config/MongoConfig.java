package com.manduljo.ohou.mongo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.manduljo.ohou.mongo.repository")
@EnableMongoAuditing
@RequiredArgsConstructor
public class MongoConfig {

  private final MongoDatabaseFactory mongoDatabaseFactory;

  private final MongoMappingContext mongoMappingContext;

  @Bean
  public MappingMongoConverter mappingMongoConverter() {
    DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
    MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
    mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
    return mappingMongoConverter;
  }

}
