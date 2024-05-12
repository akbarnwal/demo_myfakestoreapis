package in.anilbarnwal.demo_myfakestoreapis.services;

import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductRequestBody;
import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse getSingleProduct(int productIndex);

    List<ProductResponse> getAllProducts();

    ProductResponse addNewProduct(ProductRequestBody productRequestBody);

    List<String> getAllCategories();

    List<ProductResponse> getCategoryProducts(String categoryType);

    void deleteProduct(int productIndex);

    ProductResponse updateProduct(ProductRequestBody productRequestBody, int productId);
}
