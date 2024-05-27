package in.anilbarnwal.demo_myfakestoreapis.services;

import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductRequestBody;
import in.anilbarnwal.demo_myfakestoreapis.dtos.FakeStoreResponseDto;
import in.anilbarnwal.demo_myfakestoreapis.exceptions.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts() throws ProductNotFoundException;

    Product addNewProduct(ProductRequestBody productRequestBody);

    List<String> getAllCategories();

    List<FakeStoreResponseDto> getCategoryProducts(String categoryType) throws ProductNotFoundException;

    Product deleteProduct(Long productId) throws ProductNotFoundException;

    FakeStoreResponseDto updateProduct(ProductRequestBody productRequestBody, int productId);
}
