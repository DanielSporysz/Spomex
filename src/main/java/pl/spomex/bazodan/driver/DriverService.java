package pl.spomex.bazodan.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.spomex.bazodan.exception.BadRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        driverRepository.findAll().forEach(drivers::add);
        return drivers;
    }

    public Driver getDriver(Integer id) {
        return driverRepository.findById(id).orElse(null);
    }

    public Driver addDriver(Driver driver) throws BadRequest {
        validateDriver(driver);
        return driverRepository.save(driver);
    }

    public Driver updateDriver(Driver driver, Integer id) throws BadRequest {
        if (driver.getId()==null || !driver.getId().equals(id)){
            throw new BadRequest("ID in payload does not match the URL");
        }
        validateDriver(driver);
        return driverRepository.save(driver);
    }

    public void deleteDriver(Integer id) {
        driverRepository.deleteById(id);
    }

    private void validateDriver(Driver driver) throws BadRequest {
        if (driver.getFirstName() == null || driver.getFirstName().isEmpty()) {
            throw new BadRequest("Missing \"firstName\".");
        } else if (driver.getSurname() == null || driver.getSurname().isEmpty()) {
            throw new BadRequest("Missing \"surname\".");
        }
    }
}
