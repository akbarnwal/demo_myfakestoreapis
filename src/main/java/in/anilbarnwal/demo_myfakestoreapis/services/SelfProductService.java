package in.anilbarnwal.demo_myfakestoreapis.services;

import in.anilbarnwal.demo_myfakestoreapis.dtos.FakeStoreResponseDto;
import in.anilbarnwal.demo_myfakestoreapis.dtos.ProductRequestBody;
import in.anilbarnwal.demo_myfakestoreapis.exceptions.ProductNotFoundException;
import in.anilbarnwal.demo_myfakestoreapis.models.Category;
import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import in.anilbarnwal.demo_myfakestoreapis.repositories.CategoryRepository;
import in.anilbarnwal.demo_myfakestoreapis.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortParam, String sortBy) throws ProductNotFoundException {
//        if(sortBy == null || sortBy.isEmpty()) {
//            return productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortParam).descending()));
//        } else if (sortBy.equals("ASC")) {
//            return productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortParam).ascending()));
//        }else{
//            return productRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(sortParam).descending()));
//        }
        return productRepository.findAll(PageRequest.of
                (pageNumber, pageSize,
                        Sort.by(sortParam).descending()));
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

    //TODO: Need to implement proper
    @Override
    public List<FakeStoreResponseDto> getCategoryProducts(String categoryType) throws ProductNotFoundException {

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
    public Product updateProduct(ProductRequestBody requestBody, Long productId) throws ProductNotFoundException {
        Product product = productRepository.findByIdIs(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product with id " + productId + " not found");
        }
        if (!product.getTitle().equals(requestBody.getTitle())) {
            product.setTitle(requestBody.getTitle());
        }
        if (!product.getPrice().equals(requestBody.getPrice())) {
            product.setPrice(requestBody.getPrice());
        }
        if (!product.getDescription().equals(requestBody.getDescription())) {
            product.setDescription(requestBody.getDescription());
        }
        if (!product.getImage().equals(requestBody.getImage())) {
            product.setImage(requestBody.getImage());
        }
        if(requestBody.getCategory() != null){
            Category categoryInDb = categoryRepository.findByTitle(requestBody.getCategory());
            if (categoryInDb == null) {
                categoryInDb = new Category();
            }
            categoryInDb.setTitle(requestBody.getCategory());
            product.setCategory(categoryInDb);
        }
        productRepository.save(product);
        return product;
    }

    //TODO: PENDING: Need to implement it
    @Override
    public Product replaceProduct(Long productId, String title, String description, String price, String image, String category) throws ProductNotFoundException {
        return null;
    }
}
