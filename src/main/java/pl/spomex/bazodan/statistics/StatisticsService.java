package pl.spomex.bazodan.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.spomex.bazodan.driver.Driver;
import pl.spomex.bazodan.driver.DriverService;
import pl.spomex.bazodan.product.Product;
import pl.spomex.bazodan.product.ProductService;
import pl.spomex.bazodan.shipment.Shipment;
import pl.spomex.bazodan.shipment.ShipmentService;
import pl.spomex.bazodan.truck.TruckService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                if (stock.get(product.getName()) == null) {
                    stock.put(product.getName(), product.getQuantity());
                } else {
                    Integer value = stock.get(product.getName());

                    if (product.getDirection().equals("in")) {
                        value += product.getQuantity();
                    } else if (product.getDirection().equals("out")) {
                        value -= product.getQuantity();
                    } else {
                        throw new IllegalArgumentException("Unsupported value in Direction field");
                    }

                    stock.put(product.getName(), value);
                }
            }
        }

        return stock;
    }

    public Map<String, Integer> getProductPopularity() {
        Map<String, Integer> popularity = new HashMap<>();

        for (Shipment shipment : shipmentService.getShipmentsByDirection("out")) {
            for (Product product : shipment.getProducts()) {
                if (!product.getDirection().equals("out")) {
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

    public List<Map<String, String>> getDriversPerformance() {
        List<Map<String, String>> performances = new ArrayList<>();

        for (Driver driver : driverService.getAllDrivers()) {
            int shipmentCount = 0;
            int cumulativeProductCount = 0;
            for (Shipment shipment : driver.getShipments()) {
                shipmentCount++;
                for (Product product : shipment.getProducts()) {
                    cumulativeProductCount += product.getQuantity();
                }
            }

            //TODO better summary mapping ??
            Map<String, String> summary = new HashMap<>();
            summary.put("id", driver.getId().toString());
            summary.put("firstName", driver.getFirstName());
            summary.put("surname", driver.getSurname());
            summary.put("shipmentCount", Integer.toString(shipmentCount));
            summary.put("cumulativeProductCount", Integer.toString(cumulativeProductCount));

            performances.add(summary);
        }

        return performances;
    }
}
