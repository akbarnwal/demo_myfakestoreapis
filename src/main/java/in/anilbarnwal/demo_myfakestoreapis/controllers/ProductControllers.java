package in.anilbarnwal.demo_myfakestoreapis.controllers;

import in.anilbarnwal.demo_myfakestoreapis.dtos.*;
import in.anilbarnwal.demo_myfakestoreapis.exceptions.ProductNotFoundException;
import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import in.anilbarnwal.demo_myfakestoreapis.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductControllers {
    private final ModelMapper modelMapper;
    private final ProductService productService;

    public ProductControllers(@Qualifier("selfProductService") ProductService productService,
                              ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

//    @GetMapping("/products/{productId}")
    @GetMapping("{productId}")
    public ResponseEntity<ProductResponseDto> getSingleProduct(@PathVariable Long productId) throws ProductNotFoundException {
        Product product = productService.getSingleProduct(productId);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

//    @PostMapping("/products")
    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> addNewProduct(@RequestBody ProductRequestBody productRequestBody) {
        Product product = productService.addNewProduct(productRequestBody);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }

//    @GetMapping("/products")
    @GetMapping()
    public List<ProductResponseDto> getAllProducts() throws ProductNotFoundException {
        List<Product> products = productService.getAllProducts();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }
        return productResponseDtoList;
    }

    @GetMapping("/products-pagination")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("sortParam") String sortParam,
            @RequestParam("sortBy") String sortBy)
            throws ProductNotFoundException {
        Page<Product> products = productService.getAllProducts(pageNumber, pageSize, sortParam, sortBy);

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Product product : products.getContent()) {
            ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }
        return new ResponseEntity<>(productResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return productService.getAllCategories();
    }

    @GetMapping("/categories/{categoryType}")
    public List<FakeStoreResponseDto> getCategoryProducts(@PathVariable String categoryType) throws ProductNotFoundException {
        return productService.getCategoryProducts(categoryType);
    }

//    @DeleteMapping("products/{productId}")
    @DeleteMapping("{productId}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable Long productId) throws ProductNotFoundException {
        Product product = productService.deleteProduct(productId);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PatchMapping("{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@RequestBody ProductRequestBody productRequestBody, @PathVariable Long productId) throws ProductNotFoundException {
        Product product = productService.updateProduct(productRequestBody, productId);
        ProductResponseDto productResponseDto = convertProductToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }

    @PutMapping("{productId}")
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

//    @GetMapping("/")
//    public ResponseEntity<String> sayHello(){
//        return new ResponseEntity<>("Hello sir!", HttpStatus.OK);
//    }


}
