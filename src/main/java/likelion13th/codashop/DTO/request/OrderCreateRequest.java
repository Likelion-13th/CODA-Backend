package likelion13th.codashop.DTO.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter

@NoArgsConstructor
public class OrderCreateRequest {
    private Long itemId;
    private int quantity;
    private int mileageToUse;
}