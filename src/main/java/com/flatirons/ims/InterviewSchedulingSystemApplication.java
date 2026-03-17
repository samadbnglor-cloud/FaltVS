package com.flatirons.ims;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InterviewSchedulingSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewSchedulingSystemApplication.class, args);
    }

}