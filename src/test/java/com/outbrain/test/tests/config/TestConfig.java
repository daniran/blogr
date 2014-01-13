package com.outbrain.test.tests.config;

import com.outbrain.test.api.PostService;
import com.outbrain.test.dal.PostRepository;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by daniran on 1/13/14.
 */
public class TestConfig {
    @Bean
    public PostService createService() {
        return new PostService();
    }

    @Bean
    public PostRepository createRepository() {
        return new PostRepository();
    }

    @Bean
    public EntityManager createEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("com.outbrain.jpa.posts");
        EntityManager em = factory.createEntityManager();
        return em;
    }
}
