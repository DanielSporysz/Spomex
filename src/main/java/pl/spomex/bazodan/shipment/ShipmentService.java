package pl.spomex.bazodan.shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    public List<Shipment> getAllShipments() {
        List<Shipment> shipments = new ArrayList<>();
        shipmentRepository.findAll().forEach(shipments::add);
        return shipments;
    }

    public Shipment getShipment(Integer id) {
        return shipmentRepository.findById(id).orElse(null);
    }

    public void addShipment(Shipment shipment) {
        shipmentRepository.save(shipment);
    }

    public void updateShipment(Shipment shipment) {
        shipmentRepository.save(shipment);
    }

    public void deleteShipment(Integer id) {
        shipmentRepository.deleteById(id);
    }
}
