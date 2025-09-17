package likelion13th.codashop.DTO.response;

import likelion13th.codashop.domain.User;
import likelion13th.codashop.domain.Order;
import likelion13th.codashop.global.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class UserInfoResponse{
    private String usernickname;
    private int recentTotal;
    private int maxMilege;
    private Map<OrderStatus,Integer> orderStatusCounts;


    public static UserInfoResponse from(User user){
        Map<OrderStatus,Integer> orderStatusCounts = user.getOrders().stream().collect(Collectors.groupingBy(
                Order::getStatus,
                Collectors.collectingAndThen(Collectors.counting(),Long::intValue)
        ));
        orderStatusCounts.putIfAbsent(OrderStatus.PROCESSING,0);
        orderStatusCounts.putIfAbsent(OrderStatus.COMPLETE,0);
        orderStatusCounts.putIfAbsent(OrderStatus.CANCEL,0);

        return new UserInfoResponse(
                user.getUsernickname(),
                user.getRecentTotal(),
                user.getMaxMileage(),
                orderStatusCounts
        );
    }
}
// 유저 id, 프로바이더 id 같은 민감한 정보는 가리고 닉네임,번호등 정보만 노춢
//static매소드를 활용하여 클래스 명으로만 from을 통해 user객체와 매핑하여 response 반환