import entity.Address;
import entity.Customer;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class Tester {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.DROP_AND_CREATE);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Address address = new Address("Vejen", "Copenhagen");
        Address address2 = new Address("Vejen2", "Copenhagen");

        Address address3 = new Address("Vejen3", "Copenhagen");
        Address address4 = new Address("Vejen4", "Copenhagen");

        Customer customer = new Customer("Peter","Nielsen");
        customer.addHobby("Tennis");
        customer.addHobby("Baseball");
        customer.addHobby("Chess");
        customer.addPhone("30303030", "Private");
        customer.addPhone("31313131", "Work");

        Customer customer2 = new Customer("Lars", "Larsen");
        customer2.addHobby("Tennis");
        customer2.addHobby("Vollyball");
        customer2.addHobby("Chess");
        customer2.addPhone("34343434", "Private");
        customer2.addPhone("35353535", "Work");

        customer.addAddress(address);
        customer.addAddress(address2);
        customer2.addAddress(address3);
        customer2.addAddress(address4);

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(address);
            entityManager.persist(address2);
            entityManager.persist(customer);
            entityManager.persist(customer2);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
