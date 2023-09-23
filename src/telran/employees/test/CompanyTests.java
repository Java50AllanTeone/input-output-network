package telran.employees.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.*;


import telran.employees.dto.*;
import telran.employees.service.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyTests {
    private static final long ID1 = 123;
    private static final String DEP1 = "dep1";
    private static final int SALARY1 = 10000;
    private static final int YEAR1 = 2000;
    private static final LocalDate DATE1 = LocalDate.ofYearDay(YEAR1, 100);
    private static final long ID2 = 124;
    private static final long ID3 = 125;
    private static final long ID4 = 126;
    private static final long ID5 = 127;
    private static final String DEP2 = "dep2";
    private static final String DEP3 = "dep3";
    private static final int SALARY2 = 5000;
    private static final int SALARY3 = 15000;
    private static final int YEAR2 = 1990;
    private static final LocalDate DATE2 = LocalDate.ofYearDay(YEAR2, 100);
    private static final int YEAR3 = 2003;
    private static final LocalDate DATE3 = LocalDate.ofYearDay(YEAR3, 100);
    private static final long ID_NOT_EXIST = 10000000;
    private static final String TEST_DATA = "test.data";
    Employee empl1 = new Employee(ID1, "name", DEP1, SALARY1, DATE1);
    Employee empl2 = new Employee(ID2, "name", DEP2, SALARY2, DATE2);
    Employee empl3 = new Employee(ID3, "name", DEP1, SALARY1, DATE1);
    Employee empl4 = new Employee(ID4, "name", DEP2, SALARY2, DATE2);
    Employee empl5 = new Employee(ID5, "name", DEP3, SALARY3, DATE3);
    Employee[] employees = {empl1, empl2, empl3, empl4, empl5};
    Company company;

    final static String TEST_FILE_NAME = "test.data";
    @BeforeEach
    void setUp() throws Exception {
        company = new CompanyImpl();
        for(Employee empl: employees) {
            company.addEmployee(empl);
        }
    }

    @Test
    void testAddEmployee() {
        assertFalse(company.addEmployee(empl1));
        assertTrue(company.addEmployee(new Employee(ID_NOT_EXIST, "name", DEP1, SALARY1, DATE1)));
    }

    @Test
    void testRemoveEmployee() {
        assertNull(company.removeEmployee(ID_NOT_EXIST));
        assertEquals(empl1, company.removeEmployee(ID1));
        Employee[] expected = {empl2, empl3, empl4, empl5};
        Employee[]actual = company.getEmployees()
                .toArray(Employee[]::new);
        Arrays.sort(actual, Comparator.comparingLong(Employee::id));
        assertArrayEquals(expected, actual);
    }

    @Test
    void testGetEmployee() {
        assertEquals(empl1, company.getEmployee(ID1));
        assertNull(company.getEmployee(ID_NOT_EXIST));
    }

    @Test
    void testGetEmployees() {
        Employee[]actual = company.getEmployees()
                .toArray(Employee[]::new);
        Arrays.sort(actual, Comparator.comparingLong(Employee::id));
        assertArrayEquals(employees, actual);
    }
    @Test
    @Order(2)
    void testRestore() {
        Company newCompany = new CompanyImpl();
        newCompany.restore(TEST_DATA);
        Employee[]actual = newCompany.getEmployees()
                .toArray(Employee[]::new);
        Arrays.sort(actual, Comparator.comparingLong(Employee::id));
        assertArrayEquals(employees, actual);

    }
    @Test
    @Order(1)
    void testSave() {
        company.save(TEST_DATA);
    }
    //Tests of CW/HW #34
    @Test
    void testGetDepartmentSalaryDistribution() {
        //TODO
    }
    @Test
    void testGetSalaryDistribution() {
        company.addEmployee(new Employee(ID_NOT_EXIST, "name", DEP1, 9999, DATE1));
        SalaryDistribution sd1 = new SalaryDistribution(5000, 10000, 3);
        SalaryDistribution sd2 = new SalaryDistribution(10000, 15000, 2);
        SalaryDistribution sd3 = new SalaryDistribution(15000, 20000, 1);
        List<SalaryDistribution> expected = List.of(sd1,sd2,sd3);
        List<SalaryDistribution> actual = company.getSalaryDistribution(5000);
        assertIterableEquals(expected, actual);
    }
    @Test
    void testGetEmployeesByDepartment() {
        //TODO
    }
    @Test
    void testGetEmployeesBySalary(){
        //TODO
    }
    @Test
    void testGetEmployeesByAge(){
        //TODO
    }
    @Test
    void testUpdateSalary() {
        //TODO
    }
    @Test
    void testUpdateDepartment() {
        //TODO
    }

}