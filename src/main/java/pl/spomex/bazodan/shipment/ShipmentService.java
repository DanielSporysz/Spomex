package pl.spomex.bazodan.shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.spomex.bazodan.driver.Driver;
import pl.spomex.bazodan.driver.DriverRepository;
import pl.spomex.bazodan.exception.BadRequest;
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

    @Autowired
    private DriverRepository driverRepository;

    public List<Shipment> getAllShipments() {
        List<Shipment> shipments = new ArrayList<>();
        shipmentRepository.findAll().forEach(shipments::add);
        return shipments;
    }

    public Shipment getShipment(Integer id) {
        return shipmentRepository.findById(id).orElse(null);
    }

    public Shipment addShipment(Shipment shipment) throws BadRequest {
        validateShipment(shipment);

        Shipment newShipment = shipmentRepository.save(shipment);
        saveShipmentProducts(newShipment);

        return newShipment;
    }

    public Shipment updateShipment(Shipment shipment, Integer id) throws BadRequest {
        if (shipment.getId() == null || !shipment.getId().equals(id)) {
            throw new BadRequest("ID in payload does not match the URL");
        }
        validateShipment(shipment);

        Shipment newShipment = shipmentRepository.save(shipment);
        saveShipmentProducts(newShipment);

        return newShipment;
    }

    public Integer deleteShipment(Integer id) {
        Shipment shipment = shipmentRepository.findById(id).orElse(null);
        if (shipment != null) {
            shipment.getProducts().forEach(t -> productRepository.delete(t));
            shipmentRepository.deleteById(id);

            return id;
        }

        return null;
    }

    private void saveShipmentProducts(Shipment shipment) {
        if (shipment != null) {
            for (Product product : shipment.getProducts()) {
                product.setShipment(shipment);
                productRepository.save(product);
            }
        }
    }

    private void validateShipment(Shipment shipment) throws BadRequest {
        // validate direction
        if (shipment.getDirection() == null || shipment.getDirection().isEmpty()) {
            throw new BadRequest("Missing \"direction\".");
        } else {
            if (!(shipment.getDirection().equals("in") || shipment.getDirection().equals("out"))) {
                throw new BadRequest("Bad value in \"direction\". Expected \"in\" or \"out\"");
            }
        }
        // validate driver
        if (shipment.getDriver() == null || shipment.getDriver().getId() == null) {
            throw new BadRequest("Missing \"driver\".");
        } else {
            Driver targetDriver = driverRepository
                    .findById(shipment.getDriver().getId())
                    .orElse(null);
            if (targetDriver == null) {
                throw new BadRequest("There's no such driver (ID=" + shipment.getDriver().getId() + ")");
            }
        }
    }
}
