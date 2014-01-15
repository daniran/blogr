package org.daniran.blogr.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * MCV Config
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.daniran.blogr"})
public class MVCConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // this must be configured because the dispatcher is registered on "/"
        configurer.enable();
    }
}
