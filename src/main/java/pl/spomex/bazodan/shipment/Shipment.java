package pl.spomex.bazodan.shipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.spomex.bazodan.driver.Driver;
import pl.spomex.bazodan.product.Product;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="direction")
    private String direction;

    @ManyToOne
    @JoinColumn(name="driver_id", referencedColumnName="id")
    private Driver driver;

    @OneToMany(mappedBy = "shipment")
    private Set<Product> products = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
