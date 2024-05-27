package in.anilbarnwal.demo_myfakestoreapis.services;

import in.anilbarnwal.demo_myfakestoreapis.dtos.FakeStoreResponseDto;
import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductRequestBody;
import in.anilbarnwal.demo_myfakestoreapis.exceptions.ProductNotFoundException;
import in.anilbarnwal.demo_myfakestoreapis.models.Category;
import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import in.anilbarnwal.demo_myfakestoreapis.repositories.CategoryRepository;
import in.anilbarnwal.demo_myfakestoreapis.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Product product = productRepository.findByIdIs(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        } else {
            return product;
        }

        //Optional-2
//        Optional<Product> productOptional = productRepository.findById(productId);
//        if (productOptional.isPresent()) {
//            return productOptional.get();
//        }
//        throw new ProductNotFoundException("Product with id " + productId + " not found");
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        return productRepository.findAll();
    }

    @Override
    public Product addNewProduct(ProductRequestBody requestBody) {
        Product product = new Product();
        product.setTitle(requestBody.getTitle());
        product.setPrice(requestBody.getPrice());
        product.setDescription(requestBody.getDescription());
        product.setImage(requestBody.getImage());

        if (!requestBody.getCategory().isEmpty()) {
            Category categoryInDb = categoryRepository.findByTitle(requestBody.getCategory());
            if (categoryInDb == null) {
                categoryInDb = new Category();
            }
            categoryInDb.setTitle(requestBody.getCategory());
            product.setCategory(categoryInDb);
        }
        return productRepository.save(product);
    }

    @Override
    public List<String> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<String> list = new ArrayList<>();
        for (Category category : categories) {
            list.add(category.getTitle());
        }
        return list;
    }

    @Override
    public List<FakeStoreResponseDto> getCategoryProducts(String categoryType) throws ProductNotFoundException {
        //TODO: Will correct it later
//        Category category = categoryRepository.findByTitle(categoryType);
//        if (category == null) {
//            throw new ProductNotFoundException("Category with title " + categoryType + " not found");
//        }
//        List<Product> products = productRepository.findAllByCategory_Title(categoryType);
//        if (products.isEmpty()) {
//            throw new ProductNotFoundException("Product with title " + categoryType + " not found");
//        }
//
//        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
//        for (Product product : products) {
//            ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
//            productResponseDto.setCategory(product.getCategory().getTitle());
//            productResponseDtos.add(productResponseDto);
//        }

        return List.of();
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException{
        Product product = productRepository.findByIdIs(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }
        productRepository.delete(product);
        return product;
    }

    @Override
    public FakeStoreResponseDto updateProduct(ProductRequestBody productRequestBody, int productId) {
        return null;
    }
}
