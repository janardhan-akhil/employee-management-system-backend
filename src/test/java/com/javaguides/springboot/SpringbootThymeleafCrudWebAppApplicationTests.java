package com.javaguides.springboot;

import com.javaguides.springboot.model.Employee;
import com.javaguides.springboot.repository.EmployeeRepository;

import com.javaguides.springboot.service.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpringbootThymeleafCrudWebAppApplicationTests {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	// 1. Positive: Get all employees
	@Test
	void getAllEmployees_ShouldReturnEmployeeList() {

		Employee employee1 = mock(Employee.class);
		Employee employee2 = mock(Employee.class);

		List<Employee> employees =
				Arrays.asList(employee1, employee2);

		when(employeeRepository.findAll()).thenReturn(employees);

		List<Employee> result = employeeService.getAllEmployees();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(employees, result);

		verify(employeeRepository, times(1)).findAll();
	}

	// 2. Negative: No employees available
	@Test
	void getAllEmployees_ShouldReturnEmptyList() {

		when(employeeRepository.findAll())
				.thenReturn(Collections.emptyList());

		List<Employee> result = employeeService.getAllEmployees();

		assertNotNull(result);
		assertTrue(result.isEmpty());

		verify(employeeRepository, times(1)).findAll();
	}

	// 3. Positive: Save employee
	@Test
	void saveEmployees_ShouldSaveEmployeeSuccessfully() {

		Employee employee = mock(Employee.class);

		employeeService.saveEmployees(employee);

		verify(employeeRepository, times(1)).save(employee);
	}

	// 4. Negative: Save employee throws exception
	@Test
	void saveEmployees_ShouldThrowExceptionWhenSaveFails() {

		Employee employee = mock(Employee.class);

		when(employeeRepository.save(employee))
				.thenThrow(new IllegalStateException("Database error"));

		assertThrows(
				IllegalStateException.class,
				() -> employeeService.saveEmployees(employee)
		);

		verify(employeeRepository, times(1)).save(employee);
	}

	// 5. Positive: Get employee by existing ID
	@Test
	void getEmployeeById_ShouldReturnEmployeeWhenIdExists() {

		long employeeId = 1L;
		Employee employee = mock(Employee.class);

		when(employeeRepository.findById(employeeId))
				.thenReturn(Optional.of(employee));

		Employee result = employeeService.getEmployeeById(employeeId);

		assertNotNull(result);
		assertEquals(employee, result);

		verify(employeeRepository, times(1)).findById(employeeId);
	}

	// 6. Negative: Employee ID does not exist
	@Test
	void getEmployeeById_ShouldThrowExceptionWhenIdDoesNotExist() {

		long employeeId = 100L;

		when(employeeRepository.findById(employeeId))
				.thenReturn(Optional.empty());

		RuntimeException exception = assertThrows(
				RuntimeException.class,
				() -> employeeService.getEmployeeById(employeeId)
		);

		assertEquals(
				"Employee not found for id::" + employeeId,
				exception.getMessage()
		);

		verify(employeeRepository, times(1)).findById(employeeId);
	}

	// 7. Positive: Delete employee
	@Test
	void deleteEmployeeId_ShouldDeleteEmployeeSuccessfully() {

		long employeeId = 1L;

		employeeService.deleteEmployeeId(employeeId);

		verify(employeeRepository, times(1))
				.deleteById(employeeId);
	}

	// 8. Negative: Delete employee throws exception
	@Test
	void deleteEmployeeId_ShouldThrowExceptionWhenDeleteFails() {

		long employeeId = 100L;

		doThrow(new IllegalStateException("Database error"))
				.when(employeeRepository)
				.deleteById(employeeId);

		assertThrows(
				IllegalStateException.class,
				() -> employeeService.deleteEmployeeId(employeeId)
		);

		verify(employeeRepository, times(1))
				.deleteById(employeeId);
	}
}
