package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entity.Employee;
import com.example.service.EmpService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private EmpService empService;

//    @GetMapping("/")
//    public String index(Model m) {
//        List<Employee> list = empService.getAllEmp();
//        m.addAttribute("empList", list);
//        return "index";
//    }
    
    
    
    

    @GetMapping("/loadEmpSave")
    public String loadEmpSave() {
        return "emp_save";
    }

    @GetMapping("/EditEmp/{id}")
    public String EditEmp(@PathVariable int id, Model m) {
        Employee emp = empService.getEmpById(id);
        m.addAttribute("emp", emp);
        return "edit_emp";
    }

//    @PostMapping("/saveEmp")
//    public String saveEmp(@ModelAttribute Employee emp, HttpSession session) {
//        Employee newEmp = empService.saveEmp(emp);
//
//        if (newEmp != null) {
//            session.setAttribute("msg", "Registererd successfully");
//        } else {
//            session.setAttribute("msg", "Something wrong on server");
//        }
//
//        return "redirect:/loadEmpSave";
//    }
    
    
    
    
    @PostMapping("/saveEmp")
    public String saveEmp( @ModelAttribute Employee emp, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }

        Employee newEmp = empService.saveEmp(emp);

        if (newEmp != null) {
            session.setAttribute("msg", "Registered successfully");
            return "redirect:/"; 
        } else {
            session.setAttribute("msg", "Something wrong on server");
            return "redirect:/loadEmpSave";
        }
    }

    
    
    
    

    @PostMapping("/updateEmpDtls")
    public String updateEmp(@ModelAttribute Employee emp, HttpSession session) {
        Employee updatedEmp = empService.saveEmp(emp);

        if (updatedEmp != null) {
            session.setAttribute("msg", "Updated successfully");
        } else {
            session.setAttribute("msg", "Something wrong on server");
        }

        return "redirect:/";
    }

    @GetMapping("/deleteEmp/{id}")
    public String deleteEmp(@PathVariable int id, HttpSession session) {
        boolean deleted = empService.deleteEmp(id);
        if (deleted) {
            session.setAttribute("msg", "Deleted successfully");
        } else {
            session.setAttribute("msg", "Something wrong on server");
        }
        return "redirect:/";
    }
    
    
    
    
    @GetMapping("/")
    public String index(@RequestParam("sortBy") Optional<String> sortBy, @RequestParam("order") Optional<String> order, Model model) {
        List<Employee> empList = empService.getAllEmp(sortBy.orElse("fullName"), order.orElse("asc"));
        model.addAttribute("empList", empList);
        model.addAttribute("searchTerm","");
        return "index";
    }
    
    
    
    
    
    @PostMapping("/searchEmp")
    public String searchEmp(@RequestParam("searchTerm") String searchTerm, Model model) {
    	
    	if (searchTerm == null || searchTerm.isEmpty()) {
            return "redirect:/";
        }

    	
    	
        List<Employee> empList = empService.searchEmp(searchTerm); 
        model.addAttribute("empList", empList);
        return "index";
    }
    
    
    
    
    @GetMapping("/averageSalary")
    public String averageSalary(@RequestParam("department") String department, Model model) {
        if (department == null || department.isEmpty()) {
            return "redirect:/";
        }

        String avgSalary = empService.getAverageSalaryByDepartment(department);
        model.addAttribute("department", department);
        model.addAttribute("averageSalary", avgSalary);
        model.addAttribute("empList", empService.getAllEmp("", ""));

        return "index";
    }


    
    
    
}
