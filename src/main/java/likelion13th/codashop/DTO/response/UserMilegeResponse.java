package likelion13th.codashop.DTO.response;

import likelion13th.codashop.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMilegeResponse {
    private int maxMileage;


    public static UserMilegeResponse from(User user) {
        return new UserMilegeResponse(
                user.getMaxMileage()
        );
    }
}
//유저 마일리지와 최근 사용 금액 반환