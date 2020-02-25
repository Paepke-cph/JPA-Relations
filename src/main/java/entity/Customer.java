package entity;
/*
 * author paepke
 * version 1.0
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.persistence.*;


@Entity
@Table(name = "Customer")
@NamedNativeQuery(name = "Customer.Truncate", query = "TRUNCATE TABLE Customer")
@NamedQueries({
    @NamedQuery(name = "Customer.deleteAllRows", query = "DELETE FROM Customer"),
    @NamedQuery(name = "Customer.findByFirstName", query = "SELECT c FROM Customer c WHERE c.firstName LIKE :name"),
    @NamedQuery(name = "Customer.findByLastName", query = "SELECT c FROM Customer c WHERE c.lastName LIKE :name"),
    @NamedQuery(name = "Customer.getAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.deleteById", query = "DELETE FROM Customer ")
})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;
    @Column(name = "customer_firstName")
    private String firstName;
    @Column(name = "customer_lastName")
    private String lastName;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name="CustomerAddress",
            joinColumns=@JoinColumn(name="customer_id", referencedColumnName="customer_id"),
            inverseJoinColumns=@JoinColumn(name="address_id", referencedColumnName="address_id"))
    private List<Address> addresses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name ="Hobby", joinColumns =@JoinColumn(name="customer_id"))
    private List<String> hobbies = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name ="CustomerPhones", joinColumns = @JoinColumn(name="customer_id"))
    @MapKeyColumn(name = "phone")
    @Column(name = "phone_description")
    private Map<String, String> phones = new HashMap<>();

    public Customer() {}
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addHobby(String hobby) {
        hobbies.add(hobby);
    }

    public String getHobbies() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < hobbies.size(); i++) {
            stringBuilder.append(hobbies);
            if(i != hobbies.size()-1)
                stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }

    public void addPhone(String phoneNumber, String phoneDescription) { phones.put(phoneNumber,phoneDescription);}
    public String getPhoneDescription(String phoneNumber) { return phones.get(phoneNumber);}

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        addresses.add(address);
        address.addCustomer(this);
    }
}
