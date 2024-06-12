package in.anilbarnwal.demo_myfakestoreapis.repositories;

import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import in.anilbarnwal.demo_myfakestoreapis.repositories.projections.ProductProjection;
import in.anilbarnwal.demo_myfakestoreapis.repositories.projections.ProductWithIdTitleAndCategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    Product save(Product product);

    Product findByIdIs(Long id);
    //OR,
    Optional<Product> findById(Long id);

//    List<Product> findAllByCategory_Title(String title);
//    List<Product> findAllByCategory_Title(String categoryTitle);

    List<Product> findAllByCategory_Title(String categoryType);

    //Hibernate Query Language (HQL)
    @Query("SELECT p FROM Product p WHERE p.title = :productTitle")
    List<Product> findAllByTitle(String productTitle);

    //Native Query
    @Query(value = "SELECT * FROM Product", nativeQuery = true)
    List<Product> findAllProducts();

    @Query("SELECT p.title FROM Product p WHERE p.id = :productId")
    String findProductTitleById(Long productId);

    @Query("SELECT p.id as id, p.title as title FROM Product p WHERE p.category.title = :categoryTitle")
    List<ProductWithIdTitleAndCategoryType> findSpecificAttributes(String categoryTitle);


    @Query("SELECT p.id as id, p.title as title, p.category as category FROM Product p WHERE p.category.title = :categoryTitle")
    List<ProductProjection> findSpecificAttributes2(String categoryTitle);

    @Query("SELECT p.id as id, p.title as title, p.description as description FROM Product p WHERE p.id= :productId")
    ProductProjection findSpecificAttributes3(Long productId);
}
