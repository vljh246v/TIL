package com.demo.til.springdata.payroll;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

  private final EmployeeRepository repository;

  public DatabaseLoader(EmployeeRepository repository) {
    this.repository = repository;
  }

  @Override
  public void run(String... args) throws Exception {
    this.repository.save(new Employee("Frodo", "Baggins", "ring bearer"));
  }
}
