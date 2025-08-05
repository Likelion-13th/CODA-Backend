package likelion13th.codashop.DTO.response;

import likelion13th.codashop.domain.Category;
import likelion13th.codashop.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ItemResponseDto {
    private Long itemId;
    private String itemName;
    private int price;
    private String imagePath;
    private String brand;
    private boolean isNew;
    private List<String> categories;

    public static ItemResponseDto from(Item item) {
        return new ItemResponseDto(
                item.getId(),
                item.getItemname(),
                item.getPrice(),
                item.getImagePath(),
                item.getBrand(),
                item.isNew(),
                item.getCategories().stream().map(Category::getCategoryName)
                        .collect(Collectors.toList())
        );

    }
}
//아이템 카테고리 이름만 반환 할 수 있도록 작성