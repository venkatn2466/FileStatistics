package com.assignment.FileStatisitcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class FileStatisitcsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileStatisitcsApplication.class, args);
	}

}
