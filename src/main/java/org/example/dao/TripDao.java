package org.example.dao;
import org.example.configuration.SessionFactoryUtil;
import org.example.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class TripDao {
    //Creates a trip in the database
    public static void createTrip(Trip trip) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(trip);
            transaction.commit();
        }
    }

    //Gets a trip by its id
    public static Trip getTripById(long id) {
        Trip trip;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            trip = session.get(Trip.class, id);
            transaction.commit();
        }
        return trip;
    }

    //Displays a list of all trips in the database
    public static List<Trip> getTrips() {
        List<Trip> trips;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            trips = session.createQuery("Select c From Trip c", Trip.class)
                    .getResultList();
            transaction.commit();
        }
        return trips;
    }

    //Updates the trip in the database
    public static void updateTrip (Trip trip) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(trip);
            transaction.commit();
        }
    }

    //Deletes a trip from the database
    public static void deleteTrip(Trip trip) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(trip);
            transaction.commit();
        }
    }

    //calculates the cost of a trip
    public static double calculateCost(Trip trip)
    {
        if(trip.getTransferableItem().getType().equals(TransferableItemType.PEOPLE))
        {
            trip.setCost(trip.getTransferableItem().getType().getFare());
        }
        else
        {
            trip.setCost(trip.getTransferableItem().getWeight() * trip.getTransferableItem().getType().getFare());
        }
        return (trip.getCost());
    }

    //adds the cost of a trip to the company's income
    public static void addRevenueToCompany(Trip trip)
    {
        trip.getCompany().setIncome(trip.getCompany().getIncome() + trip.getCost());
    }

    //sorts all trips ordered by end point
    public static List<Trip> getTripsOrderedByEndPoint() {
        List<Trip> trips;
        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            trips = session.createQuery("SELECT t FROM Trip t ORDER BY t.endPoint", Trip.class)
                    .getResultList();
            transaction.commit();
        }
        return trips;
    }

    //writes the information about a trip in a file
    public static void write(Trip trip) throws IOException {

        String filename = "trips.txt";


        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("id=" + trip.getId());
            writer.println("Start Point: " + trip.getStartPoint());
            writer.println("End Point: " + trip.getEndPoint());
            writer.println("Departure date: " + trip.getDepartureDate());
            writer.println("Arrival date: " + trip.getArrivalDate());
            writer.println("Transferable item: " + trip.getTransferableItem());
            writer.println("Cost: " + trip.getCost());
            writer.println("Company: " + trip.getCompany());
            writer.println("Employee: " + trip.getEmployee());
            writer.println("Sender: " + trip.getSender());
            writer.println("Receiver: " + trip.getReceiver());
        }
    }

    //displays the file's content
    public static void read() throws IOException {
        File folder = new File(".");
        File[] files = folder.listFiles((dir, name) -> name.startsWith("trips") && name.endsWith(".txt"));
        Arrays.sort(files);
        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println();
            }
        }
    }

}
