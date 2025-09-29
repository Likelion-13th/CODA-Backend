package likelion13th.codashop.DTO.request;

import likelion13th.codashop.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@Getter

public class ItemCreateRequest {
    private String itemname;
    private int price;
    private String brand;
    private boolean isNew;
    private List<Long> categoryIds;
}
