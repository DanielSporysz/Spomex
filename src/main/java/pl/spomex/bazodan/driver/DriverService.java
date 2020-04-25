package pl.spomex.bazodan.driver;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DriverService {
    private List<Driver> drivers = Arrays.asList(
            new Driver("0", "Daniel", "Sporysz"),
            new Driver("1", "Daniel2", "Sporysz2"),
            new Driver("2", "Daniel3", "Sporysz3")
    );

    public List<Driver> getAllDrivers() {
        return drivers;
    }

    public Driver getDriver(String id) {
        return drivers.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }
}
