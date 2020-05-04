package pl.spomex.bazodan.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping(value = "/stock", produces = "application/json")
    public ResponseEntity<Object> getStockInfo() {
        Map<String, Integer> stock = statisticsService.getStock();
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @GetMapping(value = "/popularity", produces = "application/json")
    public ResponseEntity<Object> getPopularity() {
        Map<String, Integer> stock = statisticsService.getProductPopularity();
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @GetMapping(value = "/performance/drivers", produces = "application/json")
    public ResponseEntity<Object> getDriversPerformance() {
        List<Map<String, String>> performance = statisticsService.getDriversPerformance();
        return new ResponseEntity<>(performance, HttpStatus.OK);
    }
}
