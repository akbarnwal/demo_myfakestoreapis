package in.anilbarnwal.demo_myfakestoreapis.controllers;

import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductRequestBody;
import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductResponse;
import in.anilbarnwal.demo_myfakestoreapis.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductControllers {
    private ProductService productService;

    public ProductControllers(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{productIndex}")
    public ProductResponse getSingleProduct(@PathVariable int productIndex) {
        return productService.getSingleProduct(productIndex);
    }

    @PostMapping("/addNewProduct")
    public ProductResponse addNewProduct(@RequestBody ProductRequestBody productRequestBody) {
        return productService.addNewProduct(productRequestBody);
    }

    @GetMapping("/products")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return productService.getAllCategories();
    }

    @GetMapping("/products/category/{categoryType}")
    public List<ProductResponse> getCategoryProducts(@PathVariable String categoryType) {
        return productService.getCategoryProducts(categoryType);
    }

    //    @DeleteMapping("products/{productIndex}")
    @DeleteMapping("products/{productIndex}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productIndex) {
        productService.deleteProduct(productIndex);
//        if (deleted) {
//        return ResponseEntity.ok("Product deleted successfully");
//        } else {
            return ResponseEntity.notFound().build();
//        }
    }

    @PatchMapping("products/{productId}")
    public ProductResponse updateProduct(@RequestBody ProductRequestBody productRequestBody, @PathVariable int productId){
        return productService.updateProduct(productRequestBody, productId);
    }

}
