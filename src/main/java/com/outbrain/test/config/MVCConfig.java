package com.outbrain.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by daniran on 1/13/14.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.outbrain.test"})
public class MVCConfig {
}
