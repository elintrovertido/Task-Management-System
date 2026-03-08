package com.tms.taskservice.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.tms.taskservice.constants.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@RequiredArgsConstructor
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.tms.taskservice.repository")
public class MongoConfig {

    private final MongoProperties mongoProperties;

    private String getDatabase(){
        return mongoProperties.getDatabase();
    }

    @Bean
    public MongoClient mongoClient(){
        String uri = ApplicationConstants.MONGODB +
                mongoProperties.getHost() +
                ApplicationConstants.COLON +
                mongoProperties.getPort();
        return MongoClients.create(uri);
    }
}
