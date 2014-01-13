package com.outbrain.test.config;

import com.outbrain.test.api.PostService;
import com.outbrain.test.dal.PostRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * General Config
 */
@Configuration
public class CoreConfig {
    @Bean
    public PostService createService() {
        return new PostService();
    }

    @Bean
    public PostRepository createRepository() {
        return new PostRepository();
    }

    @Bean
    public EntityManagerFactory createEntityManagerFactory() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("com.outbrain.jpa.posts");
        return factory;
    }
}
