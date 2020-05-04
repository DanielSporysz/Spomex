package pl.spomex.bazodan.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.spomex.bazodan.driver.Driver;
import pl.spomex.bazodan.driver.DriverService;
import pl.spomex.bazodan.product.Product;
import pl.spomex.bazodan.product.ProductService;
import pl.spomex.bazodan.shipment.Shipment;
import pl.spomex.bazodan.shipment.ShipmentService;
import pl.spomex.bazodan.truck.Truck;
import pl.spomex.bazodan.truck.TruckService;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class StatisticsService {

    @Autowired
    private ProductService productService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private TruckService truckService;
    @Autowired
    private ShipmentService shipmentService;

    public Map<String, Integer> getStock() {
        Map<String, Integer> stock = new HashMap<>();

        for (Shipment shipment : shipmentService.getAllShipments()) {
            for (Product product : shipment.getProducts()) {
                if (product.getName() == null) {
                    // Skip products with nulls
                    continue;
                }

                if (stock.get(product.getName()) == null) {
                    stock.put(product.getName(), product.getQuantity());
                } else {
                    Integer balance = stock.get(product.getName());

                    if (product.getDirection() == null) {
                        // Skip products with nulls
                        continue;
                    } else if (product.getDirection().equals("in")) {
                        balance += product.getQuantity();
                    } else if (product.getDirection().equals("out")) {
                        balance -= product.getQuantity();
                    } else {
                        // Skip products with unsupported direction value
                        continue;
                    }

                    stock.put(product.getName(), balance);
                }
            }
        }

        return stock;
    }

    public Map<String, Integer> getProductPopularity() {
        Map<String, Integer> popularity = new HashMap<>();

        for (Shipment shipment : shipmentService.getShipmentsByDirection("out")) {
            for (Product product : shipment.getProducts()) {
                if (product.getDirection() == null || !product.getDirection().equals("out")) {
                    continue;
                }

                if (popularity.get(product.getName()) == null) {
                    popularity.put(product.getName(), product.getQuantity());
                } else {
                    Integer value = popularity.get(product.getName()) + product.getQuantity();
                    popularity.put(product.getName(), value);
                }
            }
        }

        return popularity;
    }

    public List<Map<String, String>> getDrivers30DaysPerformance() {
        List<Map<String, String>> performances = new ArrayList<>();
        LocalDate startingDate = LocalDate.now().minus(Period.ofDays(30));

        for (Driver driver : driverService.getAllDrivers()) {
            Map<String, String> summary = new LinkedHashMap<>();
            summary.put("id", driver.getId().toString());
            summary.put("firstName", driver.getFirstName());
            summary.put("surname", driver.getSurname());
            countShipmentsAndProducts(summary, driver.getShipments(), startingDate);

            performances.add(summary);
        }

        return performances;
    }

    public List<Map<String, String>> getTrucks30DaysPerformance() {
        List<Map<String, String>> performances = new ArrayList<>();
        LocalDate startingDate = LocalDate.now().minus(Period.ofDays(30));

        for (Truck truck : truckService.getAllTrucks()) {
            Map<String, String> summary = new LinkedHashMap<>();
            summary.put("id", truck.getId().toString());
            countShipmentsAndProducts(summary, truck.getShipments(), startingDate);

            performances.add(summary);
        }

        return performances;
    }

    private void countShipmentsAndProducts(Map<String, String> summary, Set<Shipment> shipments, LocalDate startingDate) {
        if (summary == null || startingDate == null) {
            throw new IllegalArgumentException("Summary and StartingDate cannot be null");
        }

        int shipmentsCount = 0;
        int productsCount = 0;
        for (Shipment shipment : shipments) {
            if (shipment.getDate() == null || shipment.getDate().isBefore(startingDate)) {
                continue;
            }

            shipmentsCount++;
            for (Product product : shipment.getProducts()) {
                productsCount += product.getQuantity();
            }
        }

        summary.put("shipmentsCount", Integer.toString(shipmentsCount));
        summary.put("productsCount", Integer.toString(productsCount));
    }
}
