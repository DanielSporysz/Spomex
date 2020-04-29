package pl.spomex.bazodan.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    @RequestMapping("/drivers")
    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @RequestMapping("/drivers/{id}")
    public Driver getDriver(@PathVariable Integer id) {
        return driverService.getDriver(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/drivers")
    public void addDriver(@RequestBody Driver driver) {
        driverService.addDriver(driver);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/drivers/{id}")
    public void updateDriver(@RequestBody Driver driver, @PathVariable Integer id) {
        driverService.updateDriver(driver);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/drivers/{id}")
    public void deleteDriver(@PathVariable Integer id) {
        driverService.deleteDriver(id);
    }
}
