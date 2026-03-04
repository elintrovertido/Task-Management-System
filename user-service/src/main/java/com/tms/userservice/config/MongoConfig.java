package com.tms.userservice.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.tms.userservice.constants.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@RequiredArgsConstructor
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.tms.userservice.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    private final MongoProperties mongoProperties;

    @Override
    protected String getDatabaseName() {
        return mongoProperties.getDatabase();
    }

    @Bean
    @Override
    public MongoClient mongoClient() {

        String uri = ApplicationConstants.MONGODB +
                mongoProperties.getHost() +
                ApplicationConstants.COLON +
                mongoProperties.getPort();

        return MongoClients.create(uri);
    }
}