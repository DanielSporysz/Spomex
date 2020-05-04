package pl.spomex.bazodan.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/products", produces = "application/json")
    public ResponseEntity<Object> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/products/{id}", produces = "application/json")
    public ResponseEntity<Object> getProduct(@PathVariable Integer id) {
        Product product = productService.getProduct(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    // TODO remove completely or implement requests validation for product post, put and delete
//    @PostMapping(value = "/products", produces = "application/json")
//    public void addProduct(@RequestBody Product product) {
//        productService.addProduct(product);
//    }
//
//    @PutMapping(value = "/products/{id}", produces = "application/json")
//    public void updateProduct(@RequestBody Product product, @PathVariable Integer id) {
//        productService.updateProduct(product);
//    }
//
//    @DeleteMapping(value = "/products/{id}", produces = "application/json")
//    public void deleteProduct(@PathVariable Integer id) {
//        productService.deleteProduct(id);
//    }
}
