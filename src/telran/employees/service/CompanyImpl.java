package telran.employees.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import telran.employees.dto.*;

public class CompanyImpl<T> implements Company {
    HashMap<Long, Employee> employees = new HashMap<>();
    HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
    TreeMap<Integer, List<Employee>> employeesAge = new TreeMap<>();
    TreeMap<Integer, List<Employee>> employeesSalary = new TreeMap<>();

    @Override
    public boolean addEmployee(Employee empl) {
        boolean res = employees.putIfAbsent(empl.id(), empl) == null;
        if (res) {
            addEmployeesAge(empl);
            addEmployeesDepartment(empl);
            addEmployeesSalary(empl);
        }

        return res;
    }

    private void addEmployeesAge(Employee empl) {
        int age = getAge(empl.birthDate());
        employeesAge.computeIfAbsent(age, k -> new LinkedList<>()).add(empl);
    }

    private void addEmployeesDepartment(Employee empl) {
        String department = empl.department();
        employeesDepartment.computeIfAbsent(department, k -> new LinkedList<>()).add(empl);
    }

    private void addEmployeesSalary(Employee empl) {
        int salary = empl.salary();
        employeesSalary.computeIfAbsent(salary, k -> new LinkedList<>()).add(empl);
    }

    @Override
    public Employee removeEmployee(long id) {
        Employee empl = employees.remove(id);
        if(empl != null) {
            removeEmployeeAge(empl);
            removeEmployeeDepartment(empl);
            removeEmployeeSalary(empl);

        }

        return empl;
    }


    private void removeEmployeeAge(Employee empl) {
        int age = getAge(empl.birthDate());
        List<Employee> list = employeesAge.get(age);
        list.remove(empl);
        if (list.isEmpty()) {
            employeesAge.remove(age);
        }
    }



    private void removeEmployeeDepartment(Employee empl) {
        String department = empl.department();
        List<Employee> list = employeesDepartment.get(department);
        list.remove(empl);
        if (list.isEmpty()) {
            employeesDepartment.remove(department);
        }
    }

    private void removeEmployeeSalary(Employee empl) {
        int salary = empl.salary();
        List<Employee> list = employeesSalary.get(salary);
        list.remove(empl);
        if (list.isEmpty()) {
            employeesSalary.remove(salary);
        }
    }

    @Override
    public Employee getEmployee(long id) {
        return employees.get(id);
    }

    @Override
    public List<Employee> getEmployees() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public List<DepartmentSalary> getDepartmentSalaryDistribution() {
        Map<String, Double> mapDepartmentAvgSalary = employeesDepartment
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()
                                .stream()
                                .mapToDouble(Employee::salary)
                                .average()
                                .orElse(0.0))
                );
        return mapDepartmentAvgSalary
                .entrySet()
                .stream()
                .map(e -> new DepartmentSalary(e.getKey(), e.getValue()))
                .sorted(Comparator.comparingDouble(DepartmentSalary::salary))
                .toList();
    }

    @Override
    public List<SalaryDistribution> getSalaryDistribution(int interval) {
        Map<Integer, Long> mapIntervalNumbers = employees
                .values()
                .stream()
                .collect(Collectors.groupingBy(e -> e.salary() / interval, Collectors.counting()));
        return mapIntervalNumbers
                .entrySet()
                .stream()
                .map(e -> new SalaryDistribution(e.getKey() * interval, e.getKey() * interval + interval, e.getValue().intValue()))
                .sorted(Comparator.comparingInt(SalaryDistribution::min))
                .toList();
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeesDepartment.get(department);
    }

    @Override
    public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
        return employeesSalary
                .subMap(salaryFrom, salaryTo)
                .values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
        return employeesAge
                .subMap(ageFrom, ageTo)
                .values()
                .stream()
                .flatMap(List::stream)
                .toList();
    }

    private int getAge(LocalDate birthDate) {

        return (int)ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }



    @Override
    public Employee updateSalary(long id, int newSalary) {
        Employee empl = removeEmployee(id);
        addEmployee(new Employee(empl.id(), empl.name(), empl.department(), newSalary, empl.birthDate()));
        return empl;
    }

    @Override
    public Employee updateDepartment(long id, String department) {
        Employee empl = removeEmployee(id);
        addEmployee(new Employee(empl.id(), empl.name(), department, empl.salary(), empl.birthDate()));
        return empl;
    }

    public Employee update(long id, Object param) {
        Employee empl = removeEmployee(id);
        Employee newEmpl = null;

        if (param instanceof Integer)
            newEmpl = new Employee(empl.id(), empl.name(), empl.department(), (int) param, empl.birthDate());
        else if (param instanceof String)
            newEmpl = new Employee(empl.id(), empl.name(), (String) param, empl.salary(), empl.birthDate());

        addEmployee(newEmpl);
        return empl;
    }

}