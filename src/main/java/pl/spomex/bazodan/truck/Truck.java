package pl.spomex.bazodan.truck;

import com.fasterxml.jackson.annotation.*;
import pl.spomex.bazodan.license.License;
import pl.spomex.bazodan.shipment.Shipment;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "truck")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "truck")
    @JsonIgnore
    private Set<Shipment> shipments = new HashSet<>();

    @OneToOne(mappedBy = "truck")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("licenseId")
    private License license;

    @JsonProperty("licenseId")
    public void setLicense(Integer id) {
        license = License.fromId(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(Set<Shipment> shipments) {
        this.shipments = shipments;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }
}
