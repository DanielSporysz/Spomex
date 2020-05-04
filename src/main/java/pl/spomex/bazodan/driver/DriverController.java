package pl.spomex.bazodan.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.spomex.bazodan.exception.BadRequest;

import java.util.List;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping(value = "/drivers", produces = "application/json")
    public ResponseEntity<Object> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping(value = "/drivers/{id}", produces = "application/json")
    public ResponseEntity<Object> getDriver(@PathVariable Integer id) {
        Driver driver = driverService.getDriver(id);
        if (driver != null) {
            return new ResponseEntity<>(driver, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Driver not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/drivers", produces = "text/plain")
    public ResponseEntity<Object> addDriver(@RequestBody Driver driver) {
        try {
            Driver savedDriver = driverService.addDriver(driver);
            return new ResponseEntity<>(
                    "Driver created (ID=" + savedDriver.getId() + ")",
                    HttpStatus.CREATED);
        } catch (BadRequest e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/drivers/{id}", produces = "text/plain")
    public ResponseEntity<Object> updateDriver(@RequestBody Driver driver, @PathVariable Integer id) {
        try {
            Driver updatedDriver = driverService.updateDriver(driver, id);
            return new ResponseEntity<>(
                    "Driver updated (ID=" + updatedDriver.getId() + ")",
                    HttpStatus.OK);
        } catch (BadRequest e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
