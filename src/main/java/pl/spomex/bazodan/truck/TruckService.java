package pl.spomex.bazodan.truck;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.spomex.bazodan.exception.BadRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class TruckService {

    @Autowired
    private TruckRepository truckRepository;

    public List<Truck> getAllTrucks() {
        List<Truck> trucks = new ArrayList<>();
        truckRepository.findAll().forEach(trucks::add);
        return trucks;
    }

    public Truck getTruck(Integer id) {
        return truckRepository.findById(id).orElse(null);
    }

    public Truck addTruck(Truck truck) throws BadRequest {
        validateTruck(truck);
        return truckRepository.save(truck);
    }

    public Truck updateTruck(Truck truck, Integer id) throws BadRequest {
        validateTruck(truck);
        if (truck.getId() == null || !truck.getId().equals(id)) {
            throw new BadRequest("ID in payload does not match the URL");
        }
        return truckRepository.save(truck);
    }

    public void deleteTruck(Integer id) {
        truckRepository.deleteById(id);
    }

    private void validateTruck(Truck truck) throws BadRequest {
        if (truck == null) {
            throw new BadRequest("Cannot construct Truck with given information");
        }

        if (truck.getCapacity() == null || truck.getCapacity() <= 0) {
            throw new BadRequest("Missing or bad value in \"load\".");
        }
    }
}
