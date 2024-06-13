package in.anilbarnwal.demo_myfakestoreapis.services;

import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductRequestBody;
import in.anilbarnwal.demo_myfakestoreapis.dtos.FakeStoreResponseDto;
import in.anilbarnwal.demo_myfakestoreapis.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    List<Product> getAllProducts() throws ProductNotFoundException;

    Page<Product> getAllProducts(int pageNumber, int pageSize, String sortParam, String sortBy) throws ProductNotFoundException;

    Product addNewProduct(ProductRequestBody productRequestBody);

    List<String> getAllCategories();

    List<FakeStoreResponseDto> getCategoryProducts(String categoryType) throws ProductNotFoundException;

    Product deleteProduct(Long productId) throws ProductNotFoundException;

    Product updateProduct(ProductRequestBody productRequestBody, Long productId) throws ProductNotFoundException;

    Product replaceProduct(Long productId, String title, String description, Double price, String image, String category) throws ProductNotFoundException;

}
