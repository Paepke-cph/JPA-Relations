package facade;
/*
 * author paepke
 * version 1.0
 */

import entity.Customer;

import java.util.List;
import javax.persistence.*;


public class CustomerFacade {

    private static CustomerFacade instance;
    private static EntityManagerFactory emf;

    private CustomerFacade() {
    }

    /**
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CustomerFacade getCustomerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CustomerFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public long getCount() {
        EntityManager entityManager = getEntityManager();
        return entityManager.createQuery("SELECT count(c) FROM Customer c", Integer.class).getSingleResult();
    }

    public List<Customer> getAll() {
        EntityManager entityManager = getEntityManager();
        return entityManager.createNamedQuery("Customer.getAll", Customer.class).getResultList();
    }

    public List<Customer> getByFirstName(String name) {
        EntityManager entityManager = getEntityManager();
        return entityManager.createNamedQuery("Customer.findByFirstName", Customer.class).getResultList();
    }

    public List<Customer> getByLastName(String name) {
        EntityManager entityManager = getEntityManager();
        return entityManager.createNamedQuery("Customer.findByLastName", Customer.class).getResultList();
    }

    public Customer getById(Integer id) {
        EntityManager entityManager = getEntityManager();
        return entityManager.find(Customer.class, id);
    }

    public void delete(Integer id) {
        EntityManager entityManager = getEntityManager();
    }

    public Customer create(Customer customer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return customer;
    }
}
