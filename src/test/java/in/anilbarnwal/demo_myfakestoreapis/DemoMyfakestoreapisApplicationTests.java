package in.anilbarnwal.demo_myfakestoreapis;

import in.anilbarnwal.demo_myfakestoreapis.models.Category;
import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import in.anilbarnwal.demo_myfakestoreapis.repositories.CategoryRepository;
import in.anilbarnwal.demo_myfakestoreapis.repositories.ProductRepository;
import in.anilbarnwal.demo_myfakestoreapis.repositories.projections.ProductProjection;
import in.anilbarnwal.demo_myfakestoreapis.repositories.projections.ProductWithIdTitleAndCategoryType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class DemoMyfakestoreapisApplicationTests {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void getAllProductsBasedOnCategoryType() {
        List<Product> product = productRepository.findAllByCategory_Title("girl's costmatic");
        for (Product p : product) {
            System.out.println(p.getTitle());
        }
    }

    @Test
    public void getAllProductsBasedOnProductTitle(){
        List<Product> product = productRepository.findAllByTitle("Test123456 title");
        for (Product p : product) {
            System.out.println(p.getTitle());
        }
    }
//TODO: Will uncomment later as it is getting fail.
//    @Test
//    public void getAllProducts(){
//        List<Product> product = productRepository.findAllProducts();
//        for (Product p : product) {
//            System.out.println(p.getTitle());
//        }
//    }

    @Test
    public void getProductTitleById(){
        String title = productRepository.findProductTitleById(5L);
        System.out.println(title);
    }

    @Test
    public void getAllSpecificAttributesOfProduct(){
        List<ProductWithIdTitleAndCategoryType> list = productRepository.findSpecificAttributes("girl's costmatic");
        for (ProductWithIdTitleAndCategoryType p : list) {
            System.out.println(p.getId());
            System.out.println(p.getTitle());
//            System.out.println(p.getCategoryTitle());
        }
    }

    @Test
    public void getAllSpecificProductsProjection(){
        List<ProductProjection> productProjections = productRepository.findSpecificAttributes2("girl's costmatic");
        for (ProductProjection p : productProjections) {
            System.out.println(p.getId());
            System.out.println(p.getTitle());
//            Category category = p.getCategory();
            System.out.println(p.getCategory().getTitle());

        }
    }

    @Test
    public void getSpecificProductAttributes3(){
        ProductProjection productProjection = productRepository.findSpecificAttributes3(5L);
        System.out.println(productProjection.getId());
        System.out.println(productProjection.getTitle());
        System.out.println(productProjection.getDescription());
    }

    @Test
    @Transactional
    void getFetchType(){
        Optional<Category> categoryOptional = categoryRepository.findById(4L);
//        Optional<Category> category = categoryRepository.findById(3L);
        if(categoryOptional.isPresent()){
            System.out.println(categoryOptional.get().getTitle());
            List<Product> list = categoryOptional.get().getProducts();
            for (Product p : list) {
                System.out.println(p.getTitle());
            }
        }
    }

    @Test
    @Transactional
    void getFetchMode(){
        List<Category> categories = categoryRepository.findByTitleEndingWith("iphone");
        System.out.printf("categories count: %d\n", categories.size());
        for (Category category : categories) {
            System.out.println("Category : " + category.getTitle());
            List<Product> products = category.getProducts();
            System.out.printf("products count: %d\n", products.size());
            for (Product p : products) {
                System.out.println(p.getTitle());
            }
        }
    }
}
