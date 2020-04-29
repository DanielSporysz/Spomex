package pl.spomex.bazodan.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

//    private List<Driver> drivers = new ArrayList<>(Arrays.asList(
//            new Driver("0", "Daniel", "Sporysz"),
//            new Driver("1", "Daniel2", "Sporysz2"),
//            new Driver("2", "Daniel3", "Sporysz3")
//    ));

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        driverRepository.findAll().forEach(drivers::add);
        return drivers;
    }

    public Driver getDriver(String id) {
        return driverRepository.findById(id).orElse(null);
    }

    public void addDriver(Driver driver) {
        driverRepository.save(driver);
    }

    public void updateDriver(Driver driver) {
        driverRepository.save(driver);
    }

    public void deleteDriver(String id) {
        driverRepository.deleteById(id);
    }
}
