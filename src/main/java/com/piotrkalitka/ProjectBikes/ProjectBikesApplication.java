package com.piotrkalitka.ProjectBikes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@SpringBootApplication
public class ProjectBikesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectBikesApplication.class, args);
    }
}