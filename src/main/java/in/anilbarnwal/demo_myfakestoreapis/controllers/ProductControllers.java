package in.anilbarnwal.demo_myfakestoreapis.controllers;

import in.anilbarnwal.demo_myfakestoreapis.dtos.*;
import in.anilbarnwal.demo_myfakestoreapis.exceptions.ProductNotFoundException;
import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import in.anilbarnwal.demo_myfakestoreapis.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductControllers {
    private final ModelMapper modelMapper;
    private final ProductService productService;

    public ProductControllers(@Qualifier("selfProductService") ProductService productService,
                              ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> getSingleProduct(@PathVariable Long productId) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(productId);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PostMapping("/addNewProduct")
    public ResponseEntity<ProductResponseDto> addNewProduct(@RequestBody ProductRequestBody productRequestBody) {
        Product product =  productService.addNewProduct(productRequestBody);
        ProductResponseDto productResponseDto =  convertProductToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() throws ProductNotFoundException {
        List<Product> products =  productService.getAllProducts();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }
        return productResponseDtoList;
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return productService.getAllCategories();
    }

    @GetMapping("/categories/{categoryType}")
    public List<FakeStoreResponseDto> getCategoryProducts(@PathVariable String categoryType) throws ProductNotFoundException {
        return productService.getCategoryProducts(categoryType);
    }

    @DeleteMapping("products/{productId}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable Long productId) throws ProductNotFoundException {
        Product product = productService.deleteProduct(productId);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PatchMapping("products/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestBody productRequestBody, @PathVariable Long productId) throws ProductNotFoundException {
        Product product = productService.updateProduct(productRequestBody, productId);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> replaceProduct(@RequestBody ProductRequestBody requestBody, @PathVariable Long productId) throws ProductNotFoundException {
        Product product = productService.replaceProduct(
                productId,
                requestBody.getTitle(),
                requestBody.getDescription(),
                requestBody.getPrice(),
                requestBody.getImage(),
                requestBody.getCategory()
        );
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    ///////////////////Mapper class //////////////
    private ProductResponseDto convertProductToProductResponseDto(Product product) {
        String catTitle = product.getCategory().getTitle();
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        productResponseDto.setCategory(catTitle);
        return productResponseDto;
    }
}
