package pl.spomex.bazodan.shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.spomex.bazodan.driver.Driver;
import pl.spomex.bazodan.driver.DriverService;
import pl.spomex.bazodan.exception.BadRequest;
import pl.spomex.bazodan.product.Product;
import pl.spomex.bazodan.product.ProductService;
import pl.spomex.bazodan.truck.Truck;
import pl.spomex.bazodan.truck.TruckService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private TruckService truckService;

    public List<Shipment> getAllShipments() {
        List<Shipment> shipments = new ArrayList<>();
        shipmentRepository.findAll().forEach(shipments::add);
        return shipments;
    }

    public Shipment getShipment(Integer id) {
        return shipmentRepository.findById(id).orElse(null);
    }

    public List<Shipment> getShipmentsByDirection(String direction){
        return shipmentRepository.findByDirection(direction);
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
            shipment.getProducts().forEach(t -> productService.deleteProduct(t.getId()));
            shipmentRepository.deleteById(id);

            return id;
        } else {
            return null;
        }
    }

    private void saveShipmentProducts(Shipment shipment) throws BadRequest {
        if (shipment != null) {
            for (Product product : shipment.getProducts()) {
                product.setShipment(shipment);
                product.setDirection(shipment.getDirection());
                productService.addProduct(product);
            }
        }
    }

    private void validateShipment(Shipment shipment) throws BadRequest {
        if (shipment == null) {
            throw new BadRequest("Cannot construct Shipment with given information");
        }

        // validate direction
        if (shipment.getDirection() == null || shipment.getDirection().isEmpty()) {
            throw new BadRequest("Missing \"direction\".");
        }
        if (!(shipment.getDirection().equals("in") || shipment.getDirection().equals("out"))) {
            throw new BadRequest("Bad value in \"direction\". Expected \"in\" or \"out\".");
        }

        // validate driver
        Driver shipmentDriver = shipment.getDriver();
        if (shipmentDriver == null || shipmentDriver.getId() == null) {
            throw new BadRequest("Missing \"driver\".");
        }
        if (driverService.getDriver(shipmentDriver.getId()) == null) {
            throw new BadRequest("There's no such driver (ID=" + shipmentDriver.getId() + ").");
        }

        // validate truck
        Truck shipmentTruck = shipment.getTruck();
        if (shipmentTruck == null || shipmentTruck.getId() == null) {
            throw new BadRequest("Missing \"truck\".");
        }
        if (truckService.getTruck(shipmentTruck.getId()) == null) {
            throw new BadRequest("There's no such truck (ID=" + shipmentTruck.getId() + ").");
        }
    }
}
