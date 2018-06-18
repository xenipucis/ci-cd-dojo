/*
 * <copyright file="NinjaApplicationConfig.java" company="SITA INC Ireland Ltd">
 *     Copyright © SITA INC Ireland Ltd 2017. Confidential. All rights reserved.
 * </copyright>
 */
package ie.dockerdonegal.ninja.configuration;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Slf4j
@Configuration
public class NinjaApplicationConfig {

  @Bean
  public Fongo createInMemoryMongo() {
    return new Fongo("InMemoryMongo");
  }

  @Bean
  public MongoClient createMongo(@Autowired final Fongo fongo) {
    return fongo.getMongo();
  }

  @Bean
  public MongoDbFactory createMongoDBFactory(@Autowired final MongoClient mongoClient) {
    return new SimpleMongoDbFactory(mongoClient, "ninja");
  }

  @Bean(name = "mongoTemplate")
  public MongoTemplate createMongoTemplate(@Autowired final MongoDbFactory mongoDBFactory) {
    return new MongoTemplate(mongoDBFactory);
  }
}
