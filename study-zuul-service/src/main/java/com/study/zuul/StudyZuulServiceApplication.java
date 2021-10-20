package com.study.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class StudyZuulServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyZuulServiceApplication.class, args);
    }

}
