package entity;
/*
 * author paepke
 * version 1.0
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


@Entity
@Table(name = "Address")
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE FROM Address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;
    @Column(name = "address_street")
    private String street;
    @Column(name = "address_city")
    private String city;

    @ManyToMany(mappedBy = "addresses")
    private List<Customer> customers = new ArrayList();

    public Address() {}

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
