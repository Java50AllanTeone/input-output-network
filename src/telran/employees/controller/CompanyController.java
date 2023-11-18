package telran.employees.controller;


import java.time.LocalDate;
import java.util.*;


import telran.employees.dto.DepartmentSalary;
import telran.employees.dto.Employee;
import telran.employees.dto.SalaryDistribution;
import telran.employees.service.Company;
import telran.view.InputOutput;
import telran.view.Item;
public class CompanyController {
    private static final long MIN_ID = 100000;
    private static final long MAX_ID = 999999;
    private static final String[] DEPARTMENTS = {"QA", "Development", "Audit", "Accounting", "Management"};
    private static final int MIN_SALARY = 7000;
    private static final int MAX_SALARY = 50000;
    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2001;
    private static Company company;
    public static ArrayList<Item> getItems(Company company) {
        CompanyController.company = company;
        List<Item> itemsList = getItemsList();
        ArrayList<Item> res = new ArrayList<>(itemsList);
        return res;
    }
    private static List<Item> getItemsList() {

        return List.of(
                Item.of("Hire new Employee", CompanyController::addEmployee),
                Item.of("Fire  Employee", CompanyController::removeEmployee),
                Item.of("Display data of Employee", CompanyController::getEmployee),
                Item.of("Display data of all Employees", CompanyController::getEmployees),
                Item.of("Distribution of salary by departments", CompanyController::getDepartmentSalaryDistribution),
                Item.of("Salary distribution per interval", CompanyController::getSalaryDistribution),
                Item.of("Display data of Employees in department", CompanyController::getEmployeesByDepartment),
                Item.of("Display data of Employees by salary", CompanyController::getEmployeesBySalary),
                Item.of("Display data of Employees by salary", CompanyController::getEmployeesByAge),
                Item.of("Update salary", CompanyController::updateSalary),
                Item.of("Update department", CompanyController::updateDepartment)
        );
    }
    static void addEmployee(InputOutput io) {
        long id = io.readLong("Enter employee identity", "Wrong identity", MIN_ID, MAX_ID);
        String name = io.readString("Enter name", "Wrong name", str -> str.matches("[A-Z][a-z]{2,}"));
        String department = io.readString("Enter department " + Arrays.deepToString(DEPARTMENTS), "Wrong department", new HashSet<String>(List.of(DEPARTMENTS)));
        int salary = io.readInt("Enter Salary", "Wrong salary", MIN_SALARY, MAX_SALARY);
        LocalDate birthDate = io.readIsoDate("Enter birtdate in ISO format", "Wrong birthdate",
                LocalDate.of(MIN_YEAR,1, 1), LocalDate.of(MAX_YEAR,12, 31));
        Employee empl = new Employee(id, name, department, salary, birthDate);
        boolean res = company.addEmployee(empl);
        io.writeLine(res ? "Employee has been added" : "Employee already exists");
    }
    static void removeEmployee(InputOutput io) {
        long id = io.readLong("Enter employee identity", "Wrong identity", MIN_ID, MAX_ID);
        Employee empl = company.removeEmployee(id);
        io.writeLine(empl != null ? "Employee has been removed" : "Employee does not exist");
    }
    static void getEmployee(InputOutput io) {
        long id = io.readLong("Enter employee identity", "Wrong identity", MIN_ID, MAX_ID);
        Employee empl = company.getEmployee(id);
        io.writeObjectLine(empl != null ? empl : "Employee does not exist");
    }
    static void getEmployees(InputOutput io) {
        List<Employee> employees = company.getEmployees();
        employees.forEach(e -> io.writeObjectLine(e));
    }
    static void getDepartmentSalaryDistribution(InputOutput io) {
        List<DepartmentSalary> sd = company.getDepartmentSalaryDistribution();
        sd.forEach(e -> io.writeObjectLine(e));
    }
    static void getSalaryDistribution(InputOutput io) {
        int interval = io.readInt("Enter interval", "Wrong interval");
        List<SalaryDistribution> sd = company.getSalaryDistribution(interval);
        sd.forEach(e -> io.writeObjectLine(e));
    }
    static void getEmployeesByDepartment(InputOutput io) {
        String department = io.readString("Enter department " + Arrays.deepToString(DEPARTMENTS), "Wrong department", new HashSet<String>(List.of(DEPARTMENTS)));
        List<Employee> empls = company.getEmployeesByDepartment(department);
        empls.forEach(e -> io.writeObject(e));
    }
    static void getEmployeesBySalary(InputOutput io) {
        int salaryFrom = io.readInt("Enter Salary From", "Wrong salary", MIN_SALARY, MAX_SALARY);
        int salaryTo = io.readInt("Enter Salary To", "Wrong salary", MIN_SALARY, MAX_SALARY);
        List<Employee> empls = company.getEmployeesBySalary(salaryFrom, salaryTo);
        empls.forEach(e -> io.writeObjectLine(e));
    }
    static void getEmployeesByAge(InputOutput io) {
        int ageFrom = io.readInt("Enter Age From", "Wrong Age",
                LocalDate.now().minusYears(MAX_YEAR).getYear(),
                LocalDate.now().minusYears(MIN_YEAR).getYear());
        int ageTo = io.readInt("Enter Age To", "Wrong Age",
                LocalDate.now().minusYears(MAX_YEAR).getYear(),
                LocalDate.now().minusYears(MIN_YEAR).getYear());

        List<Employee> empls = company.getEmployeesByAge(ageFrom, ageTo);
        empls.forEach(e -> io.writeObjectLine(e));
    }
    static void updateSalary(InputOutput io) {
        long id = io.readLong("Enter employee identity", "Wrong identity", MIN_ID, MAX_ID);
        int salary = io.readInt("Enter Salary", "Wrong salary", MIN_SALARY, MAX_SALARY);
        Employee empl = company.updateSalary(id, salary);
        io.writeLine(empl != null ? "Salary has been updated" : "Employee does not exist");
    }
    static void updateDepartment(InputOutput io) {
        long id = io.readLong("Enter employee identity", "Wrong identity", MIN_ID, MAX_ID);
        String department = io.readString("Enter department " + Arrays.deepToString(DEPARTMENTS), "Wrong department", new HashSet<String>(List.of(DEPARTMENTS)));
        Employee empl = company.updateDepartment(id, department);
        io.writeLine(empl != null ? "Department has been updated" : "Employee does not exist");
    }



}