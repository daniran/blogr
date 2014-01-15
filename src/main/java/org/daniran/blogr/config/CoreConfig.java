package org.daniran.blogr.config;

import org.daniran.blogr.api.PostService;
import org.daniran.blogr.dal.PostRepository;
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
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("org.daniran.blogr.jpa.posts");
        return factory;
    }
}
