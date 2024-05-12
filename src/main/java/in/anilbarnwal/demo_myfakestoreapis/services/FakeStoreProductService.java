package in.anilbarnwal.demo_myfakestoreapis.services;

import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductRequestBody;
import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProductResponse getSingleProduct(int productIndex) {
        ProductResponse productResponse = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productIndex,
                ProductResponse.class);
        return productResponse;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> products = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                List.class
        );
        return products;
    }

    @Override
    public ProductResponse addNewProduct(ProductRequestBody productRequestBody) {
        ProductResponse productResponse = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                productRequestBody,
                ProductResponse.class
        );
        return productResponse;
    }

    @Override
    public List<String> getAllCategories() {
        return restTemplate.getForObject(
          "https://fakestoreapi.com/products/categories",
          List.class
        );
    }

    @Override
    public List<ProductResponse> getCategoryProducts(String categoryType) {
        List<ProductResponse> products = restTemplate.getForObject(
          "https://fakestoreapi.com/products/category/" + categoryType,
                List.class
        );

        return products;
    }

    @Override
    public void deleteProduct(int productIndex) {
        restTemplate.delete(
                "https://fakestoreapi.com/products/" + productIndex
        );
//        return productResponse;
    }

    @Override
    public ProductResponse updateProduct(ProductRequestBody productRequestBody, int productId) {
        try{
            ProductResponse productResponse = restTemplate.patchForObject(
                    "https://fakestoreapi.com/products/" + productId,
                    productRequestBody,
                    ProductResponse.class
            );
            return productResponse;
        }catch(Exception e){
            e.printStackTrace();
            return new ProductResponse();
        }

    }
}
