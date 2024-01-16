package org.example.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="start point")
    private String startPoint;
    @Column(name="end point")
    private String endPoint;
    @Column(name="departure date")
    private LocalDate departureDate;
    @Column(name="arrival date")
    private LocalDate arrivalDate;
    @ManyToOne
    private TransferableItem transferableItem;
    @Column(name="cost")
    private double cost;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Client Sender;

    @ManyToOne
    private Client Receiver;

    public Trip() {
    }

    public Trip(String startPoint, String endPoint, LocalDate departureDate, LocalDate arrivalDate, TransferableItem transferableItem, Company company, Employee employee, Client sender, Client receiver) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.transferableItem = transferableItem;
        this.company = company;
        try {
            if ((employee.getEmployeeQual().equals(EmployeeQual.PEOPLE) && transferableItem.getType().equals(TransferableItemType.PEOPLE))
                    || (employee.getEmployeeQual().equals(EmployeeQual.GOODS) && transferableItem.getType().equals(TransferableItemType.GOODS))) {
                this.employee = employee;
            } else {
                throw new IllegalArgumentException("Employee has to have the needed qualifications to transport this package!");

            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(28);
        }
        this.Sender = sender;
        this.Receiver = receiver;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public TransferableItem getTransferableItem() {
        return transferableItem;
    }

    public void setTransferableItem(TransferableItem transferableItem) {
        this.transferableItem = transferableItem;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Client getSender() {
        return Sender;
    }

    public void setSender(Client sender) {
        Sender = sender;
    }

    public Client getReceiver() {
        return Receiver;
    }

    public void setReceiver(Client receiver) {
        Receiver = receiver;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", transferableItem=" + transferableItem +
                ", cost=" + cost +
                ", company=" + company +
                ", employee=" + employee +
                ", Sender=" + Sender +
                ", Receiver=" + Receiver +
                '}';
    }
}
