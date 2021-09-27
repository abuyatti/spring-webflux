package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.employee.EmployeeWebClient;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		EmployeeWebClient employeeWebClient = new EmployeeWebClient();
		employeeWebClient.consume();
	}

}
