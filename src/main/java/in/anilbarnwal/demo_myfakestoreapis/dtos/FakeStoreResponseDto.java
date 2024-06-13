package in.anilbarnwal.demo_myfakestoreapis.dtos;

import in.anilbarnwal.demo_myfakestoreapis.models.Category;
import in.anilbarnwal.demo_myfakestoreapis.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreResponseDto {
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;

    public Product toProduct() {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setPrice(price);

        Category categoryObj = new Category();
        categoryObj.setTitle(category);

        product.setCategory(categoryObj);
        product.setDescription(description);
        product.setImage(image);
        return product;

    }
}
