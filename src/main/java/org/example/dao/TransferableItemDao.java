package org.example.dao;
import org.example.configuration.SessionFactoryUtil;
import org.example.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class TransferableItemDao {
    //Creates a transferable item in the database
    public static void createTransferableItem(TransferableItem transferableItem) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(transferableItem);
            transaction.commit();
        }
    }
    //Gets a transferable item by its id
    public static TransferableItem getTransferableItemById(long id) {
        TransferableItem transferableItem;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            transferableItem = session.get(TransferableItem.class, id);
            transaction.commit();
        }
        return transferableItem;
    }

    //Displays a list of all transferable item
    public static List<TransferableItem> getTransferableItems() {
        List<TransferableItem> transferableItems;
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            transferableItems = session.createQuery("Select c From TransferableItem c", TransferableItem.class)
                    .getResultList();
            transaction.commit();
        }
        return transferableItems;
    }

    //Updates a transferable item in the database
    public static void updateTransferableItem(TransferableItem transferableItem) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(transferableItem);
            transaction.commit();
        }
    }

    //Deletes a transferable item from the database
    public static void deleteTransferableItem(TransferableItem transferableItem) {
        try(Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(transferableItem);
            transaction.commit();
        }
    }


}
