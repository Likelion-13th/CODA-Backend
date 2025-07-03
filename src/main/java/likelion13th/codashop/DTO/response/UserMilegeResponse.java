package likelion13th.codashop.DTO.response;

import likelion13th.codashop.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserMilegeResponse {
    private int maxMileage;
    private int recentTotal;

    public static UserMilegeResponse from(User user) {
        return new UserMilegeResponse(
                user.getMaxMileage(),
                user.getRecentTotal()
        );
    }
}
