package com.inditex.java.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = Application.BASE_PACKAGE)
@EntityScan(Application.BASE_PACKAGE_INFRASTRUCTURE)
@EnableJpaRepositories(basePackages = Application.BASE_PACKAGE_INFRASTRUCTURE)
@ComponentScan(basePackages = {Application.BASE_PACKAGE})
public class Application {
    public static final String BASE_PACKAGE = "com.inditex.java.spring";
    public static final String BASE_PACKAGE_INFRASTRUCTURE = Application.BASE_PACKAGE + ".infrastructure";
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}