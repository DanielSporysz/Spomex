package pl.spomex.bazodan.shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @RequestMapping(method = RequestMethod.GET, value ="/shipments")
    public List<Shipment> getAllShipment() {
        return shipmentService.getAllShipments();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shipments/{id}")
    public Shipment getShipment(@PathVariable Integer id) {
        return shipmentService.getShipment(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/shipments")
    public void addShipment(@RequestBody Shipment shipment) {
        shipmentService.addShipment(shipment);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/shipments/{id}")
    public void updateShipment(@RequestBody Shipment shipment, @PathVariable Integer id) {
        shipmentService.updateShipment(shipment);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/shipments/{id}")
    public void deleteShipment(@PathVariable Integer id) {
        shipmentService.deleteShipment(id);
    }
}
