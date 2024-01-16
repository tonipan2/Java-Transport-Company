package org.example.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name="vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @OneToOne
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    public Vehicle() {
    }

    public Vehicle(VehicleType vehicleType, Employee employee, Company company) {
        this.vehicleType = vehicleType;
        try {
            if((vehicleType.equals(VehicleType.BUS) && employee.getEmployeeQual().equals(EmployeeQual.PEOPLE))
                    || (vehicleType.equals(VehicleType.TRUCK) && employee.getEmployeeQual().equals(EmployeeQual.GOODS)))
                this.employee = employee;
            else {
                throw new IllegalArgumentException("Employee has to have the needed qualifications to drive this vehicle!");

            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(33);
        }
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicleType=" + vehicleType +
                ", employee=" + employee +
                ", company=" + company +
                '}';
    }
}

