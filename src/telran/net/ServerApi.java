package telran.net;

public interface ServerApi {
    public static final String EMPLOYEE_ADD = "employee/add";
    public static final String EMPLOYEE_GET = "employee/get";
    public static final String EMPLOYEES_ALL = "employees/all";
    public static final String EMPLOYEE_SALARY_UPDATE = "employee/salary/update";
    public static final String EMPLOYEE_DEPARTMENT_UPDATE = "employee/department/update";
    public static final String EMPLOYEE_REMOVE = "employee/remove";
    public static final String GET_DEPARTMENT_SALARY_DISTIBUTION = "employee/get/departmentsalarydistribution";
    public static final String GET_SALARY_DISTRIBUTION = "employee/get/salarydistribution";
    public static final String GET_EMPLOYEES_BY_DEPARTMENT = "employees/department";
    public static final String GET_EMPLOYEES_BY_SALARY = "employees/salary";
    public static final String GET_EMPLOYEES_BY_AGE = "employees/age";
}
