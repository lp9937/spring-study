package com.study.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class StudyConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyConfigClientApplication.class, args);
    }

}
