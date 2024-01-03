package telran.employees;

import java.io.Serializable;
import java.util.ArrayList;

import telran.employees.dto.*;
import telran.employees.service.Company;
import telran.net.*;

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
		Integer defaultValue = Integer.MAX_VALUE;
		try {
			responseData = switch (requestType) {
			case ServerApi.EMPLOYEE_ADD -> employee_add(requestData);
				case ServerApi.EMPLOYEE_GET -> employee_get(requestData);
				case ServerApi.EMPLOYEES_ALL -> employees_all(requestData);
				case ServerApi.EMPLOYEE_SALARY_UPDATE -> employee_salary_update(requestData);
				case ServerApi.EMPLOYEE_REMOVE -> employee_remove(requestData);
				case ServerApi.GET_DEPARTMENT_SALARY_DISTIBUTION -> employee_dep_sal_distribution(requestData);
				case ServerApi.GET_SALARY_DISTRIBUTION -> employee_salary_distribution(requestData);
				case ServerApi.GET_EMPLOYEES_BY_DEPARTMENT -> employees_department(requestData);
				case ServerApi.GET_EMPLOYEES_BY_SALARY -> employees_salary(requestData);
				case ServerApi.GET_EMPLOYEES_BY_AGE -> employees_age(requestData);
				case ServerApi.EMPLOYEE_DEPARTMENT_UPDATE -> employee_department_update(requestData);
			default -> defaultValue;
			};
			response = responseData == (Integer)0 ? new Response(ResponseCode.WRONG_TYPE, requestType)
					: new Response(ResponseCode.OK, responseData);
		} catch (Exception e) {
			response = new Response(ResponseCode.WRONG_DATA, e.getMessage());
		}

		return response;
	}

	private Serializable employee_department_update(Serializable requestData) {
		UpdateDepartmentData data = (UpdateDepartmentData) requestData;
		long id = data.id();
		String newDepartment = data.newDepartment();
		return company.updateDepartment(id, newDepartment);
	}

	private Serializable employees_age(Serializable requestData) {
		return new ArrayList<>(company.getEmployeesByAge(((IntervalData)requestData).from(), ((IntervalData)requestData).to()));

	}

	private Serializable employees_salary(Serializable requestData) {
		return new ArrayList<>(company.getEmployeesBySalary(((IntervalData)requestData).from(), ((IntervalData)requestData).to()));
	}

	private Serializable employees_department(Serializable requestData) {
		return new ArrayList<>(company.getEmployeesByDepartment(requestData.toString()));
	}

	private Serializable employee_salary_distribution(Serializable requestData) {
		return new ArrayList<>(company.getSalaryDistribution((int) requestData));
	}

	private Serializable employee_dep_sal_distribution(Serializable requestData) {
		return new ArrayList<>(company.getDepartmentSalaryDistribution());
	}

	private Serializable employee_remove(Serializable requestData) {
		return company.removeEmployee((long) requestData);
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

}
