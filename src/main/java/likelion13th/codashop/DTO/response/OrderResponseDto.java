package likelion13th.codashop.DTO.response;

import likelion13th.codashop.domain.Order;
import likelion13th.codashop.global.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private String usernickname;
    private String item_name;
    private String item_brand;
    private String item_url;
    private int quantity;
    private int totalPrice;
    private int finalPrice;
    private int mileageToUse; //사용한 마일리지
    private OrderStatus status;
    private LocalDateTime createdAt;

    public static OrderResponseDto from(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getUser().getUsernickname(),
                order.getItem().getItemname(),
                order.getItem().getBrand(),
                order.getItem().getImagePath(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getFinalPrice(),
                order.getTotalPrice() - order.getFinalPrice(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}
//fun1(1,2,3,4,5,6,7)