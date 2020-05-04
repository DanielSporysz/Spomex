package pl.spomex.bazodan.truck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.spomex.bazodan.exception.BadRequest;

import java.util.List;

@RestController
public class TruckController {

    @Autowired
    private TruckService truckService;

    @GetMapping(value = "/trucks", produces = "application/json")
    public ResponseEntity<Object> getAllTrucks() {
        List<Truck> trucks = truckService.getAllTrucks();
        return new ResponseEntity<>(trucks, HttpStatus.OK);
    }

    @GetMapping(value = "/trucks/{id}", produces = "application/json")
    public ResponseEntity<Object> getTrick(@PathVariable Integer id) {
        Truck trucks = truckService.getTruck(id);
        if (trucks != null) {
            return new ResponseEntity<>(trucks, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Truck not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/trucks", produces = "text/plain")
    public ResponseEntity<Object> addTruck(@RequestBody Truck truck) {
        try {
            Truck savedTruck = truckService.addTruck(truck);
            return new ResponseEntity<>(
                    "Truck created (ID=" + savedTruck.getId() + ")",
                    HttpStatus.CREATED);
        } catch (BadRequest e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/trucks/{id}", produces = "text/plain")
    public ResponseEntity<Object> updateTruck(@RequestBody Truck truck, @PathVariable Integer id) {
        try {
            Truck updatedTruck = truckService.updateTruck(truck, id);
            return new ResponseEntity<>(
                    "Truck updated (ID=" + updatedTruck.getId() + ")",
                    HttpStatus.OK);
        } catch (BadRequest e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
