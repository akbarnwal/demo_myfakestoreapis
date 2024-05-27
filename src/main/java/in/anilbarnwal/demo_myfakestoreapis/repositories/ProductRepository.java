package in.anilbarnwal.demo_myfakestoreapis.repositories;

import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Product save(Product product);

    Product findByIdIs(Long id);
    //OR,
    Optional<Product> findById(Long id);

//    List<Product> findAllByCategory_Title(String title);
    List<Product> findAllByCategory_Title(String categoryTitle);
}
