package likelion13th.codashop.DTO.response;

import likelion13th.codashop.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse{
    private String usernickname;
    private String phoneNumber;
    private int recentTotal;
    private boolean deletable;


    public static UserInfoResponse from(User user){
        return new UserInfoResponse(
                user.getUsernickname(),
                user.getPhoneNumber(),
                user.getRecentTotal(),
                user.isDeletable()
        );
    }
}
// 유저 id, 프로바이더 id 같은 민감한 정보는 가리고 닉네임,번호등 정보만 노춢
//static매소드를 활용하여 클래스 명으로만 from을 통해 user객체와 매핑하여 response 반환