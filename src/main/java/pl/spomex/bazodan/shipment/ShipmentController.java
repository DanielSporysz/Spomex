package pl.spomex.bazodan.shipment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.spomex.bazodan.driver.Driver;
import pl.spomex.bazodan.exception.BadRequest;

import java.util.List;

@RestController
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @GetMapping(value ="/shipments", produces = "application/json")
    public ResponseEntity<Object> getAllShipment() {
        List<Shipment> shipments = shipmentService.getAllShipments();
        return new ResponseEntity<>(shipments, HttpStatus.OK);
    }

    @GetMapping(value = "/shipments/{id}", produces = "application/json")
    public ResponseEntity<Object> getShipment(@PathVariable Integer id) {
        Shipment shipment = shipmentService.getShipment(id);
        if (shipment != null) {
            return new ResponseEntity<>(shipment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Shipment not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/shipments", produces = "text/plain")
    public ResponseEntity<Object> addShipment(@RequestBody Shipment shipment) {
        try {
            Shipment savedShipment = shipmentService.addShipment(shipment);
            return new ResponseEntity<>(
                    "Shipment created (ID=" + savedShipment.getId() + ")",
                    HttpStatus.CREATED);
        } catch (BadRequest e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/shipments/{id}", produces = "text/plain")
    public ResponseEntity<Object> updateShipment(@RequestBody Shipment shipment, @PathVariable Integer id) {
        try {
            Shipment updatedShipment = shipmentService.updateShipment(shipment, id);
            return new ResponseEntity<>(
                    "Shipment updated (ID=" + id + ")",
                    HttpStatus.CREATED);
        } catch (BadRequest e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping( value = "/shipments/{id}", produces = "text/plain")
    public ResponseEntity<Object> deleteShipment(@PathVariable Integer id) {
        Integer result = shipmentService.deleteShipment(id);
        if(result.equals(id)){
            return new ResponseEntity<>("Shipment has been deleted (ID="+id+")", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There's no such shipment (ID="+id+")", HttpStatus.NOT_FOUND);
        }
    }
}
