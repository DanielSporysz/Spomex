package pl.spomex.bazodan.shipment;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShipmentRepository extends CrudRepository<Shipment, Integer> {
    List<Shipment> findByDirection(String direction);
}
