package pl.spomex.bazodan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class SpomexAPI {
    public static void main(String[] args) {
        SpringApplication.run(SpomexAPI.class, args);
    }
}
