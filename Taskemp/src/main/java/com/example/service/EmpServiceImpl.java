package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.entity.Employee;
import com.example.repository.EmpRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpRepository empRepo;

    @Override
    public Employee saveEmp(Employee emp) {
        return empRepo.save(emp);
    }
//
//    @Override
//    public List<Employee> getAllEmp() {
//        return empRepo.findAll();
//    }

    @Override
    public Employee getEmpById(int id) {
        return empRepo.findById(id).get();
    }

    @Override
    public boolean deleteEmp(int id) {
        boolean exists = empRepo.existsById(id);
        if (exists) {
            empRepo.deleteById(id);
            return true;
        }
        return false;
    }
    
    
    
    
    @Override
    public List<Employee> getAllEmp(String sortBy, String order) {
    	if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "id";
        }
        if (order == null || order.isEmpty()) {
            order = "asc";
        }
        Sort sort = Sort.by(sortBy);
        if (order.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }
        return empRepo.findAll(sort);
    }
    
    
    
    
    @Override
    public List<Employee> searchEmp(String searchTerm) {
        // Assuming you want to search by full name or any part of it
        return empRepo.findAll(Sort.by(Sort.Direction.ASC, "fullName"))
                      .stream()
                      .filter(emp -> emp.getFullName().toLowerCase().contains(searchTerm.toLowerCase()))
                      .collect(Collectors.toList());
    }
    
    
    
    
    @Override
    public String getAverageSalaryByDepartment(String department) {
        List<Employee> employeesInDepartment = empRepo.findByDepartment(department);
        if (employeesInDepartment.isEmpty()) {
            return "No employees found in this department"; // Or handle it differently
        }

        double totalSalary = employeesInDepartment.stream()
                                                  .mapToDouble(Employee::getSalary)
                                                  .sum();
        double averageSalary = totalSalary / employeesInDepartment.size();
        return String.format("%.2f", averageSalary); // Format to two decimal places
    }
    
    
    
    
    
    
    
    
    public void removeSessionMessage() {
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();

		session.removeAttribute("msg");

	}
    

}
