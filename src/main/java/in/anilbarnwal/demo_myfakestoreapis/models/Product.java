package in.anilbarnwal.demo_myfakestoreapis.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel{
    private String title;
    private String price;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Category category;

    private String description;
    private String image;
}