package ru.cereevse.kyrs.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.cereevse.kyrs.exceptions.ExceptionHandler;
import ru.cereevse.kyrs.model.Employee;
import ru.cereevse.kyrs.repository.EmployeeRepository;
import ru.cereevse.kyrs.servive.EmployeeServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ExceptionHandler exceptionHandler;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addEmployee() {
        Employee employee = new Employee();
        employee.setIdEmployee(1L);
        employee.setNameEmployee("Sem");
        employee.setSurnameEmployee("Rasell");
        employee.setTelNumberEmployee("122333456");

        // Mock the save method
        when(employeeRepository.save(employee)).thenReturn(employee);

        employeeService.addEmployee(employee);

        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void getAllEmployees() {
        Employee employee1 = new Employee();
        employee1.setIdEmployee(1L);
        employee1.setNameEmployee("Sem");
        employee1.setSurnameEmployee("Rasell");

        Employee employee2 = new Employee();
        employee2.setIdEmployee(2L);
        employee2.setNameEmployee("Alla");
        employee2.setSurnameEmployee("Croft");

        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getEmployeeById() {
        Employee employee = new Employee();
        employee.setIdEmployee(1L);
        employee.setNameEmployee("Sem");
        employee.setSurnameEmployee("Rasell");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getEmployeeById(1L);

        assertTrue(result.isPresent());
        assertEquals("Sem", result.get().getNameEmployee());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void putEmployeeById() {
        Employee existingEmployee = new Employee();
        existingEmployee.setIdEmployee(1L);
        existingEmployee.setNameEmployee("Sem");
        existingEmployee.setSurnameEmployee("Rasell");

        Employee updatedEmployee = new Employee();
        updatedEmployee.setNameEmployee("Alla");
        updatedEmployee.setSurnameEmployee("Croft");
        updatedEmployee.setTelNumberEmployee("987654444");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(existingEmployee)).thenReturn(existingEmployee);

        Optional<Employee> result = employeeService.putEmployeeById(1L, updatedEmployee);

        assertTrue(result.isPresent());
        assertEquals("Alla", result.get().getNameEmployee());
        assertEquals("Croft", result.get().getSurnameEmployee());
        assertEquals("987654444", result.get().getTelNumberEmployee());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(existingEmployee);
    }

    @Test
    void deleteEmployeeById() {
        // Mock the deleteById method
        doNothing().when(employeeRepository).deleteById(1L);

        employeeService.deleteEmployeeById(1L);

        verify(employeeRepository, times(1)).deleteById(1L);
    }
}