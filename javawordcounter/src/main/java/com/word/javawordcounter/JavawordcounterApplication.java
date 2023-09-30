package com.word.javawordcounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = "com.word.javawordcounter")
public class JavawordcounterApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavawordcounterApplication.class, args);
	}

}
