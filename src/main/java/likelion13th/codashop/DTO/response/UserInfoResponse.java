package likelion13th.codashop.DTO.response;

import likelion13th.codashop.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse{
    private Long userId;
    private String usernickname;
    private String providerId;
    private String phoneNumber;
    private int recentTotal;
    private boolean deletable;


    public static UserInfoResponse from(User user){
        return new UserInfoResponse(
                user.getId(),
                user.getUsernickname(),
                user.getProviderId(),
                user.getPhoneNumber(),
                user.getRecentTotal(),
                user.isDeletable()
        );
    }
}
