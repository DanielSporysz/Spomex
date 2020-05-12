package pl.spomex.bazodan.shipment;

import com.fasterxml.jackson.annotation.JsonFormat;
import pl.spomex.bazodan.driver.Driver;
import pl.spomex.bazodan.product.Product;
import pl.spomex.bazodan.truck.Truck;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="Europe/Warsaw")
    private LocalDate date;

    @Column(name = "direction")
    private String direction;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "truck_id", referencedColumnName = "id")
    private Truck truck;

    @OneToMany(mappedBy = "shipment")
    private List<Product> products = new ArrayList<>();

    public static Shipment fromId(Integer id){
        Shipment shipment = new Shipment();
        shipment.setId(id);
        return  shipment;
    }

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
