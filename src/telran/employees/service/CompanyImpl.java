package telran.employees.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import telran.employees.dto.*;

public class CompanyImpl implements Company {
    HashMap<Long, Employee> employees = new HashMap<>();
    HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
    TreeMap<Integer, List<Employee>> employeesAge = new TreeMap<>();
    TreeMap<Integer, List<Employee>> employeesSalary = new TreeMap<>();

    LocalDate lastAgeRefresh = LocalDate.now();

    @Override
    public boolean addEmployee(Employee empl) {
        boolean res = employees.putIfAbsent(empl.id(), empl) == null;
        if (res) {
            addToIndexMap(empl, employeesAge, e -> getAge(e.birthDate()));
            addToIndexMap(empl, employeesDepartment, Employee::department);
            addToIndexMap(empl, employeesSalary, Employee::salary);
        }
        return res;
    }

    private <T> void addToIndexMap(Employee empl, Map<T, List<Employee>> indexMap, Function<Employee, T> keyFn) {
        T key = keyFn.apply(empl);
        indexMap.computeIfAbsent(key, k -> new LinkedList<>()).add(empl);
    }


    @Override
    public Employee removeEmployee(long id) {
        Employee empl = employees.remove(id);
        if(empl != null) {
            removeFromIndexMap(empl, employeesAge, e -> getAge(e.birthDate()));
            removeFromIndexMap(empl, employeesDepartment, Employee::department);
            removeFromIndexMap(empl, employeesSalary, Employee::salary);
        }
        return empl;
    }

    private <T> void removeFromIndexMap(Employee empl, Map<T, List<Employee>> indexMap, Function<Employee, T> keyFn) {
        T key = keyFn.apply(empl);
        List<Employee> list = indexMap.get(key);
        list.remove(empl);

        if (list.isEmpty()) {
            indexMap.remove(key);
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
        refreshAg+e();
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


    private void refreshAge() {
        if (lastAgeRefresh.isBefore(LocalDate.now())) {
            employeesAge.clear();
            employees.values().forEach(e -> addToIndexMap(e, employeesAge, f -> getAge(f.birthDate())));
            lastAgeRefresh = LocalDate.now();
        }
    }

}