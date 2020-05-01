package pl.spomex.bazodan.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.spomex.bazodan.shipment.Shipment;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "direction")
    private String direction;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="shipment_id", referencedColumnName="id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Shipment shipment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
