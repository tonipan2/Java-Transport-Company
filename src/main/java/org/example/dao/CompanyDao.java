package org.example.dao;


import jakarta.persistence.criteria.*;
import org.example.configuration.SessionFactoryUtil;
import org.example.entity.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class CompanyDao {

    //Creating a company in the database
    public static void createCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
        }
    }

    //Getting a company by its id
    public static Company getCompanyById(long id) {
        Company company;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            company = session.get(Company.class, id);
            transaction.commit();
        }
        return company;
    }

    //Displaying all companies in the database
    public static List<Company> getCompanies() {
        List<Company> companies;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            companies = session.createQuery("Select c From Company c", Company.class)
                    .getResultList();
            transaction.commit();
        }
        return companies;
    }

    //Updating a company in the database
    public static void updateCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(company);
            transaction.commit();
        }
    }

    //Deleting a company from the database
    public static void deleteCompany(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(company);
            transaction.commit();
        }
    }

    //Sorting all companies in the database by their names
    public static List<Company> getCompaniesOrderedByName() {
        List<Company> companies;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            companies = session.createQuery("SELECT c FROM Company c ORDER BY c.name", Company.class)
                    .getResultList();
            transaction.commit();
        }
        return companies;
    }

    //Sorting all companies in the database by their revenue
    public static List<Company> getCompaniesOrderedByRevenue() {
        List<Company> companies;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            companies = session.createQuery("SELECT c FROM Company c ORDER BY c.revenue", Company.class)
                    .getResultList();
            transaction.commit();
        }
        return companies;
    }

    //Sorting all employees in a company by their salary
    public static List<Employee> getEmployeesSortedBySalary(Company company) {
        List<Employee> employees = Collections.emptyList();
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

            Join<Employee, Company> companyJoin = employeeRoot.join("company");

            criteriaQuery.where(criteriaBuilder.equal(companyJoin.get("id"), company.getId()));

            criteriaQuery.orderBy(criteriaBuilder.desc(employeeRoot.get("salary")));

            employees = session.createQuery(criteriaQuery).getResultList();

            for (Employee employee : employees) {
                Hibernate.initialize(employee.getCompany());
            }

            transaction.commit();
        }
        return employees;
    }

    //Sorting all employees in a company by their qualification
    public static List<Employee> getEmployeesSortedByQual(Company company) {
        List<Employee> employees = Collections.emptyList();
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
            Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

            Join<Employee, Company> companyJoin = employeeRoot.join("company");

            criteriaQuery.where(criteriaBuilder.equal(companyJoin.get("id"), company.getId()));

            criteriaQuery.orderBy(criteriaBuilder.asc(employeeRoot.get("employeeQual")));

            employees = session.createQuery(criteriaQuery).getResultList();

            for (Employee employee : employees) {
                Hibernate.initialize(employee.getCompany());
            }

            transaction.commit();
        }
        return employees;
    }

    //displays all trips made in the company
    public static long getTotalTripsCount(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Trip> tripRoot = criteriaQuery.from(Trip.class);

            Join<Trip, Company> companyJoin = tripRoot.join("company");

            criteriaQuery.where(criteriaBuilder.equal(companyJoin.get("id"), company.getId()));

            criteriaQuery.select(criteriaBuilder.count(tripRoot));

            return session.createQuery(criteriaQuery).uniqueResult();
        }
    }

    //displays all trips made by a certain employee
    public static List<Object[]> getDriverTripsCount(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Trip> tripRoot = criteriaQuery.from(Trip.class);

            Join<Trip, Employee> employeeJoin = tripRoot.join("employee");

            criteriaQuery.where(criteriaBuilder.equal(tripRoot.get("company"), company));

            criteriaQuery.groupBy(employeeJoin.get("id"));
            criteriaQuery.multiselect(employeeJoin.get("name"), criteriaBuilder.count(tripRoot));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    //Displays a company's income for a selected period of time
    public static double getCompanyIncomeForPeriod(Company company, LocalDate startDate, LocalDate endDate) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);
            Root<Trip> tripRoot = criteriaQuery.from(Trip.class);

            Join<Trip, Company> companyJoin = tripRoot.join("company");

            criteriaQuery.where(
                    criteriaBuilder.equal(companyJoin.get("id"), company.getId()),
                    criteriaBuilder.between(tripRoot.get("departureDate"), startDate, endDate)
            );

            criteriaQuery.select(criteriaBuilder.sum(tripRoot.get("cost")));

            return session.createQuery(criteriaQuery).uniqueResult();
        }
    }

    //Displays what income a certain employee has generated for the company
    public static List<Object[]> getDriverIncomes(Company company) {
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<Trip> tripRoot = criteriaQuery.from(Trip.class);

            Join<Trip, Employee> employeeJoin = tripRoot.join("employee");

            criteriaQuery.where(criteriaBuilder.equal(tripRoot.get("company"), company));

            criteriaQuery.groupBy(employeeJoin.get("id"));
            criteriaQuery.multiselect(employeeJoin.get("name"), criteriaBuilder.sum(tripRoot.get("cost")));

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

}

