package pl.spomex.bazodan.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.spomex.bazodan.exception.BadRequest;
import pl.spomex.bazodan.shipment.Shipment;
import pl.spomex.bazodan.shipment.ShipmentService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShipmentService shipmentService;

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    public Product getProduct(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public void addProduct(Product product) throws BadRequest {
        validateProduct(product);
        productRepository.save(product);
    }

    public void updateProduct(Product product, Integer id) throws BadRequest {
        validateProduct(product);
        if (!id.equals(product.getId())) {
            throw new BadRequest("ID in payload doesn't match the URL.");
        }
        productRepository.save(product);
    }

    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    private void validateProduct(Product product) throws BadRequest {
        if (product == null) {
            throw new BadRequest("Cannot construct Product with given information");
        }

        // validate name
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new BadRequest("Missing \"name\".");
        }

        // validate direction
        if (product.getDirection() == null || product.getDirection().isEmpty()) {
            throw new BadRequest("Missing or bad value in \"direction\".");
        }
        if (!(product.getDirection().equals("in") || product.getDirection().equals("out"))) {
            throw new BadRequest("Bad value in \"direction\". Expected \"in\" or \"out\".");
        }

        // validate shipment
        if (product.getShipment() == null) {
            throw new BadRequest("Missing \"shipment id\".");
        }
        Shipment shipmentTarget = shipmentService.getShipment(product.getShipment().getId());
        if (shipmentTarget == null) {
            throw new BadRequest("There's no such shipment (ID=" + product.getShipment().getId() + ").");
        }

        // validate quantity
        if (product.getQuantity() == null || product.getQuantity() <= 0) {
            throw new BadRequest("Missing or bad value in \"quantity\".");
        }
    }
}
