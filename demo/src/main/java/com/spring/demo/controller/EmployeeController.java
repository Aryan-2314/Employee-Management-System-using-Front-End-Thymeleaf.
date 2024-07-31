package com.spring.demo.controller;

import com.spring.demo.entity.Employee;
import com.spring.demo.service.EmployeeService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //Getting list of employees
    @GetMapping("/list-of-all-employees")
    public String getAllEmployees(Model model) {
        List<Employee> employeeList = employeeService.findAll();
        if (employeeList == null) {
            throw new RuntimeException("Employee not found");
        }
        model.addAttribute("employees", employeeList);
        return "employees/list-of-all-employees";
    }

    @GetMapping("/showFormAdd")
    public String showFormAdd(Model model)
    {
        Employee employee = new Employee();
        model.addAttribute("employee",employee);
        return "employees/employees-form";
    }

    @GetMapping("/showFormUpdate")
    public String showFormUpdate(@RequestParam("employeeId") int id,Model model)
    {
        //get the employee from the service
        Employee employee = employeeService.findById(id);
        //set employee in the model to prepopulate the form
        model.addAttribute("employee",employee);
        //send over to our form
        return "employees/employees-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("employee") Employee employee)
    {
        //save the employee
        employeeService.save(employee);
        //use a redirect to prevent the duplicate submission
        return "redirect:/employee/list";
    }

    @DeleteMapping("/employees/delete")
    public String delete(@RequestParam("employeeId") int id)
    {
        //delete the employee
        employeeService.delete(id);
        //redirect to the //employees/list
        return "redirect:/employees/list";
    }
}
