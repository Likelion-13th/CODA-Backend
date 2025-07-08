package likelion13th.codashop.DTO.response;

import likelion13th.codashop.domain.Category;
import likelion13th.codashop.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class ItemResponseDto {
    private Long itemId;
    private String itemName;
    private int price;
    private String imagePath;
    private String brand;
    private boolean isNew;
    private List<Category> categories;

    public static ItemResponseDto from(Item item) {
        return new ItemResponseDto(
                item.getId(),
                item.getItemname(),
                item.getPrice(),
                item.getImagePath(),
                item.getBrand(),
                item.isNew(),
                item.getCategories()
        );

    }
}
