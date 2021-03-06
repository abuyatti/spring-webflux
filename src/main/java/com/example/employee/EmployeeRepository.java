package com.example.employee;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class EmployeeRepository {

	static Map<String, Employee> employeeData;

	static {
		employeeData = new HashMap<>();
		employeeData.put("1", new Employee("1", "Employee 1"));
		employeeData.put("2", new Employee("2", "Employee 2"));
		employeeData.put("3", new Employee("3", "Employee 3"));
	}

	public Mono<Employee> findEmployeeById(String id) {
		return Mono.just(employeeData.get(id));
	}

	public Flux<Employee> findAllEmployees() {
		return Flux.fromIterable(employeeData.values());
	}

	public Mono<Employee> updateEmployee(Employee employee) {
		Employee existingEmployee = employeeData.get(employee.getId());
		if (existingEmployee != null) {
			existingEmployee.setName(employee.getName());
		}
		return Mono.just(existingEmployee);
	}

}
