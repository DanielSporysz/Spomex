package pl.spomex.bazodan.shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.spomex.bazodan.product.Product;
import pl.spomex.bazodan.product.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Shipment> getAllShipments() {
        List<Shipment> shipments = new ArrayList<>();
        shipmentRepository.findAll().forEach(shipments::add);
        return shipments;
    }

    public Shipment getShipment(Integer id) {
        return shipmentRepository.findById(id).orElse(null);
    }

    public void addShipment(Shipment shipment) {
        updateShipment(shipment);
    }

    public void updateShipment(Shipment shipment) {
        Shipment newShipment = shipmentRepository.save(shipment);
        for (Product product : newShipment.getProducts()) {
            product.setShipment(newShipment);
            productRepository.save(product);
        }
    }

    public void deleteShipment(Integer id) {
        Shipment shipment = shipmentRepository.findById(id).orElse(null);
        if (shipment != null) {
            shipment.getProducts().forEach(t -> productRepository.delete(t));
            shipmentRepository.deleteById(id);
        }
    }
}
