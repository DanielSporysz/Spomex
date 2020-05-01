package pl.spomex.bazodan.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/products", produces = "application/json")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(value = "/products/{id}", produces = "application/json")
    public Product getProduct(@PathVariable Integer id) {
        return productService.getProduct(id);
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
