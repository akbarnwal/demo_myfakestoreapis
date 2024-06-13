package in.anilbarnwal.demo_myfakestoreapis.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private int id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;
}
