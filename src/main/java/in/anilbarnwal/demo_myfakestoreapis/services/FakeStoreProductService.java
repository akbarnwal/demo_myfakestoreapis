package in.anilbarnwal.demo_myfakestoreapis.services;

import in.anilbarnwal.demo_myfakestoreapis.models.Category;
import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductRequestBody;
import in.anilbarnwal.demo_myfakestoreapis.dtos.FakeStoreResponseDto;
import in.anilbarnwal.demo_myfakestoreapis.exceptions.ProductNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {

    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId,
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
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortParam, String sortBy) throws ProductNotFoundException {
        return null;
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
    public Product deleteProduct(Long productId) {
        restTemplate.delete(
                "https://fakestoreapi.com/products/" + productId
        );
//        return productResponse;
        return null;
    }

//    @Override
//    public Product updateProduct(ProductRequestBody productRequestBody, Long productId) {
//        try{
//            FakeStoreResponseDto fakeStoreResponseDto = restTemplate.patchForObject(
//                    "https://fakestoreapi.com/products/" + productId,
//                    productRequestBody,
//                    FakeStoreResponseDto.class
//            );
//            return fakeStoreResponseDto;
//        }catch(Exception e){
//            e.printStackTrace();
//            return new FakeStoreResponseDto();
//        }
//    }

    //TODO: Need to correct it later
    @Override
    public Product updateProduct(ProductRequestBody productRequestBody, Long productId) throws ProductNotFoundException {
        return null;
    }

    @Override
    public Product replaceProduct(Long productId, String title, String description, String price, String image, String category) throws ProductNotFoundException {
        FakeStoreResponseDto requestDto = new FakeStoreResponseDto();
        requestDto.setCategory(category);
        requestDto.setDescription(description);
        requestDto.setImage(image);
        requestDto.setPrice(price);
        requestDto.setTitle(title);

        // create request entity to send in put request body to fakestore
        HttpEntity<FakeStoreResponseDto> requestEntity = new HttpEntity<>(requestDto);

        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + productId,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreResponseDto.class
        ).getBody();

        if(fakeStoreResponseDto == null){
            throw new ProductNotFoundException("Error : Product Not found");
        }
        return fakeStoreResponseDto.toProduct();
    }


}
