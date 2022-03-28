package com.AfterDrawaing.serviceuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing
public class ServiceUserApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ServiceUserApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){return new RestTemplate();}

}
