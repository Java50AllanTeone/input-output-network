package telran.employees;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import telran.employees.dto.*;
import telran.employees.service.Company;
import telran.net.ApplProtocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CompanyProtocol implements ApplProtocol {
	private Company company;

	public CompanyProtocol(Company company) {
		this.company = company;
	}

	@Override
	public Response getResponse(Request request) {
		Serializable requestData = request.requestData();
		String requestType = request.requestType();
		Response response = null;
		Serializable responseData = 0;

		try {
			responseData = (Serializable) getMethod(requestType).invoke(this, requestData);
			response = new Response(ResponseCode.OK, responseData);
		} catch (NoSuchMethodException e) {
			response = new Response(ResponseCode.WRONG_TYPE, requestType);
		} catch (Exception e) {
			response = new Response(ResponseCode.WRONG_DATA, e.getMessage());
		}
		return response;
	}

	private Serializable employees_department_update(Serializable requestData) {
		UpdateDepartmentData data = (UpdateDepartmentData) requestData;
		return company.updateDepartment(data.id(), data.newDepartment());
	}

	private Serializable employees_age_get(Serializable requestData) {
		int[] ages = (int[])requestData;
		return new ArrayList<>(company.getEmployeesByAge(ages[0], ages[1]));
	}

	private Serializable employees_salary_get(Serializable requestData) {
		int[] salaries= (int[])requestData;
		return new ArrayList<>(company.getEmployeesBySalary(salaries[0], salaries[1]));
	}

	private Serializable employees_department_get(Serializable requestData) {
		String department = (String) requestData;
		return new ArrayList<>(company.getEmployeesByDepartment(department));
	}

	private Serializable employees_salary_distribution(Serializable requestData) {
		int interval = (int) requestData;
		return new ArrayList<>(company.getSalaryDistribution(interval));
	}

	private Serializable employees_department_salary_distribution(Serializable requestData) {

		return new ArrayList<>(company.getDepartmentSalaryDistribution());
	}

	private Serializable employee_remove(Serializable requestData) {
		long id = (long) requestData;
		return company.removeEmployee(id);
	}

	private Serializable employee_salary_update(Serializable requestData) {
		UpdateSalaryData data = (UpdateSalaryData) requestData;
		long id = data.id();
		int newSalary = data.newSalary();
		return company.updateSalary(id, newSalary);
	}

	private Serializable employees_all(Serializable requestData) {

		return new ArrayList<>(company.getEmployees());
	}

	private Serializable employee_get(Serializable requestData) {
		long id = (long) requestData;
		return company.getEmployee(id);
	}

	private Serializable employee_add(Serializable requestData) {
		Employee empl = (Employee) requestData;
		return company.addEmployee(empl);
	}


//	int value = Integer.parseInt(args[1]);
//				Method method = Test.class.getDeclaredMethod(args[0], int.class);
//				Test test = new Test();
//				method.setAccessible(true);
//				method.invoke(test, value);

	private static Method getMethod(String requestType) throws NoSuchMethodException {
		String[] arg = requestType.split("/");
		String mName = String.join("_", arg);
		Method method = CompanyProtocol.class.getDeclaredMethod(mName, Serializable.class);
		return method;
	}

}
