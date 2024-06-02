package in.anilbarnwal.demo_myfakestoreapis.repositories.projections;

import in.anilbarnwal.demo_myfakestoreapis.models.Category;

public interface ProductProjection {
    Long getId();
    String getTitle();
    String getDescription();
    String getImageUrl();
    Double getPrice();
    Category getCategory();

}
