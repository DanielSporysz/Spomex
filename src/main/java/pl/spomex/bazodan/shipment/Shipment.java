package pl.spomex.bazodan.shipment;

import pl.spomex.bazodan.driver.Driver;
import javax.persistence.*;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    private Driver driver;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
