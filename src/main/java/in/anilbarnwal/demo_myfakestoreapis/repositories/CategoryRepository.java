package in.anilbarnwal.demo_myfakestoreapis.repositories;

import in.anilbarnwal.demo_myfakestoreapis.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByTitle(String title);

    List<Category> findAll();
//    List<Category> findAlls();

}
