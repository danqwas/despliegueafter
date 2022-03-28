package com.afterdrawaing.serviceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing
public class ServiceProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProjectApplication.class, args);
    }
    @Bean
    public RestTemplate restTemplate(){return new RestTemplate();}
}
