package in.anilbarnwal.demo_myfakestoreapis.services;

import in.anilbarnwal.demo_myfakestoreapis.models.Category;
import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductRequestBody;
import in.anilbarnwal.demo_myfakestoreapis.dtos.FakeStoreResponseDto;
import in.anilbarnwal.demo_myfakestoreapis.exceptions.ProductNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public Product getSingleProduct(int productIndex) throws ProductNotFoundException {
        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productIndex,
                FakeStoreResponseDto.class);
        if(fakeStoreResponseDto == null){
            throw new ProductNotFoundException("Error : Product Not found");
        }
        return fakeStoreResponseDto.toProduct();
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        FakeStoreResponseDto[] fakeStoreResponseDtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
//                List.class
                FakeStoreResponseDto[].class
        );

        if(fakeStoreResponseDtos == null || fakeStoreResponseDtos.length == 0){
            throw new ProductNotFoundException("Error : Product list is empty");
        }

        List<Product> productList = new ArrayList<>();
        for (FakeStoreResponseDto fakeStore : fakeStoreResponseDtos) {
            Product product = modelMapper.map(fakeStore, Product.class);

            Category category = new Category();
            category.setTitle(fakeStore.getCategory());
            product.setCategory(category);

            productList.add(product);
        }
        return productList;
    }

    @Override
    public Product addNewProduct(ProductRequestBody productRequestBody) {
        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.postForObject(
                "https://fakestoreapi.com/products",
                productRequestBody,
                FakeStoreResponseDto.class
        );
        return fakeStoreResponseDto.toProduct();
    }

    @Override
    public List<String> getAllCategories() {
        return restTemplate.getForObject(
          "https://fakestoreapi.com/products/categories",
          List.class
        );
    }

    @Override
    public List<FakeStoreResponseDto> getCategoryProducts(String categoryType) throws ProductNotFoundException {
        List<FakeStoreResponseDto> products = restTemplate.getForObject(
          "https://fakestoreapi.com/products/category/" + categoryType,
                List.class
        );

        if(products == null || products.isEmpty()){
            throw new ProductNotFoundException("Error: Product category not found. Please try different product types");
        }
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
    public FakeStoreResponseDto updateProduct(ProductRequestBody productRequestBody, int productId) {
        try{
            FakeStoreResponseDto fakeStoreResponseDto = restTemplate.patchForObject(
                    "https://fakestoreapi.com/products/" + productId,
                    productRequestBody,
                    FakeStoreResponseDto.class
            );
            return fakeStoreResponseDto;
        }catch(Exception e){
            e.printStackTrace();
            return new FakeStoreResponseDto();
        }

    }
}
