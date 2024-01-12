package com.den;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.den" })
public class SpringPostgresqlDomaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPostgresqlDomaApplication.class, args);
	}

}
