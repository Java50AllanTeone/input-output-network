package telran.employees.service;

import java.nio.file.*;
import java.util.List;

import telran.employees.dto.*;
import java.io.*;
public interface Company {
    boolean addEmployee(Employee empl);
    Employee removeEmployee(long id);
    Employee getEmployee(long id);
    List<Employee> getEmployees();

    @SuppressWarnings("unchecked")
    default void restore(String dataFile) {
        if (Files.exists(Path.of(dataFile))) {
            try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(dataFile))) {
                List<Employee> employeesRestore = (List<Employee>) stream.readObject();
                employeesRestore.forEach(e -> addEmployee(e));
            }catch(Exception e) {
                throw new RuntimeException(e.toString());
            }
        }
    }

    default void save(String dataFile) {

        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            stream.writeObject(getEmployees());
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    List<DepartmentSalary> getDepartmentSalaryDistribution();
    List<SalaryDistribution> getSalaryDistribution(int interval);
    List<Employee> getEmployeesByDepartment(String department);
    List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo);
    List<Employee> getEmployeesByAge(int ageFrom, int ageTo);
    Employee updateSalary(long id, int newSalary);
    Employee updateDepartment(long id, String department);
}