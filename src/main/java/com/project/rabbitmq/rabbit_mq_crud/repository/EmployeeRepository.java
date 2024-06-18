package com.project.rabbitmq.rabbit_mq_crud.repository;

import com.project.rabbitmq.rabbit_mq_crud.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
