package in.anilbarnwal.demo_myfakestoreapis;

import in.anilbarnwal.demo_myfakestoreapis.models.Category;
import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import in.anilbarnwal.demo_myfakestoreapis.repositories.CategoryRepository;
import in.anilbarnwal.demo_myfakestoreapis.repositories.ProductRepository;
import in.anilbarnwal.demo_myfakestoreapis.repositories.projections.ProductProjection;
import in.anilbarnwal.demo_myfakestoreapis.repositories.projections.ProductWithIdTitleAndCategoryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    @Test
    public void getAllProducts(){
        List<Product> product = productRepository.findAllProducts();
        for (Product p : product) {
            System.out.println(p.getTitle());
        }
    }

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



}
