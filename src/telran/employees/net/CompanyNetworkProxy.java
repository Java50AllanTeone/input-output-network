package telran.employees.net;

import java.util.List;

import telran.employees.dto.DepartmentSalary;
import telran.employees.dto.Employee;
import telran.employees.dto.SalaryDistribution;
import telran.employees.dto.UpdateSalaryData;
import telran.employees.service.Company;
import telran.net.NetworkHandler;
import telran.net.ServerApi;

public class CompanyNetworkProxy implements Company {
	private NetworkHandler networkHandler;

	public CompanyNetworkProxy(NetworkHandler networkHandler) {
		this.networkHandler = networkHandler;
	}

	@Override
	public boolean addEmployee(Employee empl) {
		return networkHandler.send(ServerApi.EMPLOYEE_ADD, empl);
	}

	@Override
	public Employee removeEmployee(long id) {
		return networkHandler.send(ServerApi.EMPLOYEE_REMOVE, id);
	}

	@Override
	public Employee getEmployee(long id) {

		return networkHandler.send(ServerApi.EMPLOYEE_GET, id);
	}

	@Override
	public List<Employee> getEmployees() {

		return networkHandler.send(ServerApi.EMPLOYEES_ALL, null);
	}

	@Override
	public List<DepartmentSalary> getDepartmentSalaryDistribution() {
		return networkHandler.send(ServerApi.GET_DEPARTMENT_SALARY_DISTIBUTION, null);
	}

	@Override
	public List<SalaryDistribution> getSalaryDistribution(int interval) {
		return networkHandler.send(ServerApi.GET_SALARY_DISTRIBUTION, interval);
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee updateSalary(long id, int newSalary) {

		return networkHandler.send(ServerApi.EMPLOYEE_SALARY_UPDATE, new UpdateSalaryData(id, newSalary));
	}

	@Override
	public Employee updateDepartment(long id, String department) {
		// TODO Auto-generated method stub
		return null;
	}

}
