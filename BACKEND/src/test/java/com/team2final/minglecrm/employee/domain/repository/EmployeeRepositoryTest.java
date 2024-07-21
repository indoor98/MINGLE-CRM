package com.team2final.minglecrm.employee.domain.repository;

import com.team2final.minglecrm.common.config.querydsl.QueryDslConfig;
import com.team2final.minglecrm.employee.domain.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QueryDslConfig.class)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("existsByEmail() Test")
    void testExistsByEmail() {
        // Given
        Employee employee = Employee.builder()
                .name("testEmployee")
                .email("test@example.com")
                .password("TestPassword13@#!")
                .authority("ROLE_MANAGAER")
                .build();

        employeeRepository.save(employee);

        // When
        boolean exists = employeeRepository.existsByEmail("test@example.com");

        // Then
        assertTrue(exists);
    }

    @Test
    @DisplayName("findByEmail() Test")
    void testFindByEmail() {

        // Given
        Employee employee = Employee.builder()
                .name("testEmployee")
                .email("test@example.com")
                .password("TestPassword13@#!")
                .authority("ROLE_MANAGAER")
                .build();

        employeeRepository.save(employee);

        // When
        Optional<Employee> foundEmployee = employeeRepository.findByEmail("test@example.com");

        // Then
        assertThat(foundEmployee).isNotEmpty();
        assertThat(foundEmployee.get().getEmail()).isEqualTo("test@example.com");
    }


    @Test
    void testFindByEmail_NotFound() {
        // Given

        // When
        Optional<Employee> foundEmployee = employeeRepository.findByEmail("notfound@example.com");

        // Then
        assertThat(foundEmployee).isNotPresent();
    }


}