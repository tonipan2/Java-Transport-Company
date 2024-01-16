package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="employee")
public class Employee  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name cannot be blank!")
    @Pattern(regexp = "^([A-Z]).*", message = "Name has to start with capital letter!")
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @Column(name="qualification")
    private EmployeeQual employeeQual;

    @Column(name="salary")
    private double salary;

    public Employee() {
    }
    public Employee(String name, Company company, EmployeeQual employeeQual, double salary) {

        this.name = name;
        this.company = company;
        this.employeeQual = employeeQual;
        this.salary = salary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public EmployeeQual getEmployeeQual() {
        return employeeQual;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
        this.getCompany().setExpenses(this.getCompany().getExpenses() + this.salary);
    }



    public void setEmployeeQual(EmployeeQual employeeQual) {
        this.employeeQual = employeeQual;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company=" + company +
                ", employeeQual=" + employeeQual +
                ", salary=" + salary +
                '}';
    }
}
